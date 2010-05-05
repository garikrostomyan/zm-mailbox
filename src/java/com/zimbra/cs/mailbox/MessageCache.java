/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010 Zimbra, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
/*
 * Created on Nov 13, 2005
 */
package com.zimbra.cs.mailbox;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.SharedByteArrayInputStream;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ByteUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.db.DbMailItem;
import com.zimbra.cs.mime.Mime;
import com.zimbra.cs.mime.MimeVisitor;
import com.zimbra.cs.stats.ZimbraPerf;
import com.zimbra.cs.store.BlobInputStream;
import com.zimbra.cs.store.StoreManager;
import com.zimbra.cs.util.JMSession;

/**
 * @author dkarp
 */
public class MessageCache {

    /**
     * Summary of the states of a <tt>CacheNode</tt>:
     * <ul>
     *   <li><b>RAW</b>: No attempt has been made to expand the message.  Raw message data is available
     *     via either <tt>mContent</tt> or <tt>mMessage</tt> or both.</li>
     *   <li><b>EXPANDED</b>: <tt>mMessage</tt> references the expanded <tt>MimeMessage</tt>.
     *     <tt>mContent</tt> may contain original raw data.</li>
     *   <li><b>BOTH</b>: Expansion was attempted, but had no effect.  The same data will be returned,
     *     regardless of whether the caller requests an expanded message or not.</li>
     * </ul>
     */
    private static enum ConvertedState { RAW, EXPANDED, BOTH };
    private static final int STREAMED_MESSAGE_SIZE = 4096;

    private static final class CacheNode {
        long mSize;
        byte[] mContent;
        MimeMessage mMessage;
        ConvertedState mConvertersRun;

        CacheNode(long size, byte[] content)                            { mSize = size;  mContent = content; }
        CacheNode(long size, MimeMessage mm, ConvertedState converted)  { mSize = size;  mMessage = mm;  mConvertersRun = converted; }
    }

    private static final int DEFAULT_CACHE_SIZE = 16 * 1000 * 1000;
    
    private static Map<String, CacheNode> mCache = new LinkedHashMap<String, CacheNode>(150, (float) 0.75, true);
    private static int mTotalSize = 0;
    private static int mMaxCacheSize;
    static {
        try {
            mMaxCacheSize = Provisioning.getInstance().getLocalServer().getIntAttr(Provisioning.A_zimbraMessageCacheSize, DEFAULT_CACHE_SIZE);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    /** Uncaches any data associated with the given item.  This must be done
     *  before you change the item's content; otherwise, the cache will return
     *  stale data. */
    static void purge(MailItem item) {
        purge(item.getDigest());
    }
    
    /** Uncaches any data associated with the given item.  This must be done
     *  before you change the item's content; otherwise, the cache will return
     *  stale data. */
    static void purge(Mailbox mbox, int itemId) {
        String digest = null;
        try {
            digest = DbMailItem.getBlobDigest(mbox, itemId);
        } catch (ServiceException e) {
            ZimbraLog.cache.warn("Unable to uncache message for item", e);
        }
        if (digest != null) {
            purge(digest);
        }
    }
    
    /** Uncaches any data associated with the given item.  This must be done
     *  before you change the item's content; otherwise, the cache will return
     *  stale data. */
    static void purge(String digest) {
        CacheNode cnode = null;
        synchronized (mCache) {
            cnode = mCache.remove(digest);
            if (cnode != null) {
                mTotalSize -= cnode.mSize;
            }
        }
        
        if (cnode == null) {
            ZimbraLog.cache.debug("msgcache: attempted to purge %s but could not find it in the cache.", digest);
        } else {
            ZimbraLog.cache.debug("msgcache: purged %s, size=%d.", digest, cnode.mSize);
        }
    }
    
    /** Returns the raw, uncompressed content of the item.  For messages,
     *  this is the body as received via SMTP; no postprocessing has been
     *  performed to make opaque attachments (e.g. TNEF) visible.
     * 
     * @throws ServiceException when the message file does not exist.
     * @see #getMimeMessage()
     * @see #getRawContent() */
    static byte[] getItemContent(MailItem item) throws ServiceException {
        String key = item.getDigest();
        ZimbraLog.cache.debug("msgcache: getItemContent(): id=%d, size=%d, digest=%s.", item.getId(), item.getSize(), key);
        if (key == null || key.equals(""))
            return null;

        boolean cacheHit = false;
        CacheNode cnode = null;
        synchronized (mCache) {
            cnode = mCache.get(key);
            cacheHit = cnode != null && cnode.mContent != null;

            if (!cacheHit && cnode != null) {
                mCache.remove(key);  mTotalSize -= cnode.mSize;
                ZimbraLog.cache.debug(
                    "msgcache: can't use a cached MimeMessage because of TNEF conversion.  Removing item with size=%d.  Cache size is now %d.",
                    cnode.mSize, mTotalSize);
            }
        }

        if (!cacheHit) {
            try {
                // wasn't cached; fetch, cache, and return it
                long size = item.getSize();
                if (size > Integer.MAX_VALUE)
                    throw MailServiceException.MESSAGE_TOO_BIG(Integer.MAX_VALUE, size);
                InputStream is = fetchFromStore(item);
                cnode = new CacheNode(size, ByteUtil.getContent(is, (int) size));

                // cache the message content (if it'll fit)
                cacheItem(key, cnode);
            } catch (IOException e) {
                throw ServiceException.FAILURE("IOException while retrieving content for item " + item.getId(), e);
            }
        } else {
            if (ZimbraLog.cache.isDebugEnabled())
                ZimbraLog.cache.debug("msgcache: found raw content in cache: " + item.getDigest());
        }
        
        ZimbraPerf.COUNTER_MBOX_MSG_CACHE.increment(cacheHit ? 100 : 0);
        
        return cnode.mContent;
    }

    /** Returns an {@link InputStream} of the raw, uncompressed content of
     *  the item.  For messages, this is the body as received via SMTP; no
     *  postprocessing has been performed to make opaque attachments (e.g.
     *  TNEF) visible.
     * 
     * @throws ServiceException when the message file does not exist.
     * @see #getMimeMessage()
     * @see #getItemContent() */
    static InputStream getRawContent(MailItem item) throws ServiceException {
        String key = item.getDigest();
        ZimbraLog.cache.debug("msgcache: getRawContent(): id=%d, size=%d, digest=%s.", item.getId(), item.getSize(), key);
        if (key == null || key.equals(""))
            return null;
        if (item.getSize() < mMaxCacheSize) {
            // Content is small enough to be cached in memory
            return new SharedByteArrayInputStream(getItemContent(item));
        }
        try {
            return fetchFromStore(item);
        } catch (IOException e) {
            String msg = String.format("Unable to get content for %s %d", item.getClass().getSimpleName(), item.getId());
            throw ServiceException.FAILURE(msg, e);
        }
    }
    
    /** Returns a JavaMail {@link javax.mail.internet.MimeMessage}
     *  encapsulating the message content.  If possible, TNEF and uuencoded
     *  attachments are expanded and their components are presented as
     *  standard MIME attachments.  If TNEF or uuencode decoding fails, the
     *  MimeMessage wraps the raw message content.
     * 
     * @return A MimeMessage wrapping the RFC822 content of the Message.
     * @throws ServiceException when errors occur opening, reading,
     *                          uncompressing, or parsing the message file,
     *                          or when the file does not exist.
     * @see #getRawContent()
     * @see #getItemContent()
     * @see com.zimbra.cs.mime.TnefConverter
     * @see com.zimbra.cs.mime.UUEncodeConverter */
    static MimeMessage getMimeMessage(MailItem item, boolean expand) throws ServiceException {
        String key = item.getDigest();
        ZimbraLog.cache.debug("msgcache: getMimeMessage(): id=%d, size=%d, digest=%s, expand=%b.", item.getId(), item.getSize(), key, expand);
        boolean cacheHit = false;
        CacheNode cnode = null, cnOrig = null;
        synchronized (mCache) {
            cnode = cnOrig = mCache.get(key);
            if (cnode != null && cnode.mMessage != null) {
                cacheHit = cnode.mConvertersRun == ConvertedState.BOTH || cnode.mConvertersRun == (expand ? ConvertedState.EXPANDED : ConvertedState.RAW);
                ZimbraLog.cache.debug("msgcache: found node: size=%d, convertersRun=%s, cacheHit=%b", cnode.mSize, cnode.mConvertersRun, cacheHit);
            }

            if (!cacheHit && cnode != null) {
                mCache.remove(key);  mTotalSize -= cnode.mSize;
                ZimbraLog.cache.debug("msgcache: replacing the cached byte array with a MimeMessage.  New cache size=%d.", mTotalSize);
            }
        }

        if (!cacheHit) {
        	InputStream is = null;
            try {
                // wasn't cached; fetch the content and create the MimeMessage
                long size = item.getSize();
                if (expand && cnOrig != null && cnOrig.mMessage != null && cnOrig.mConvertersRun == ConvertedState.RAW) {
                    ZimbraLog.cache.debug("msgcache: switching from RAW to EXPANDED");
                    cnode = new CacheNode(cnOrig.mSize, cnOrig.mMessage, ConvertedState.BOTH);
                } else {
                    // use the raw byte array to construct the MimeMessage if possible, else read from disk
                    if (cnOrig == null || cnOrig.mContent == null) {
                        is = fetchFromStore(item);
                        if (is instanceof BlobInputStream)
                            size = STREAMED_MESSAGE_SIZE;
                    } else {
                        ZimbraLog.cache.debug("msgcache: creating MimeMessage from existing content");
                        is = new SharedByteArrayInputStream(cnOrig.mContent);
                    }
                    
                    cnode = new CacheNode(size, new Mime.FixedMimeMessage(JMSession.getSession(), is), expand ? ConvertedState.BOTH : ConvertedState.RAW);
                }

                if (expand) {
                    try {
                        // handle UUENCODE and TNEF conversion here...
                        for (Class visitor : MimeVisitor.getConverters()) {
                            if (((MimeVisitor) visitor.newInstance()).accept(cnode.mMessage)) {
                                cnode.mConvertersRun = ConvertedState.EXPANDED;
                                size = item.getSize(); // Even with BlobInputStream, an expanded message will be stored in memory
                                ZimbraLog.cache.debug("msgcache: expanded MimeMessage, new size=%d.", size);
                            }
                        }
                    } catch (Exception e) {
                        // if the conversion bombs for any reason, revert to the original
                        ZimbraLog.mailbox.warn("MIME converter failed for message " + item.getId(), e);

                        if (cnOrig == null || cnOrig.mContent == null) {
                            is = fetchFromStore(item);
                            if (is instanceof BlobInputStream)
                                size = STREAMED_MESSAGE_SIZE;
                        } else {
                            is = new SharedByteArrayInputStream(cnOrig.mContent);
                        }
                        
                        cnode = new CacheNode(size, new MimeMessage(JMSession.getSession(), is), ConvertedState.BOTH);
                    }
                }

                // cache the MimeMessage (if it'll fit)
                cacheItem(key, cnode);
            } catch (IOException e) {
                throw ServiceException.FAILURE("IOException while retrieving content for item " + item.getId(), e);
            } catch (MessagingException e) {
                throw ServiceException.FAILURE("MessagingException while creating MimeMessage for item " + item.getId(), e);
            } finally {
                if (!(is instanceof BlobInputStream)) {
                    ByteUtil.closeStream(is);
                }
            }
        }

        ZimbraPerf.COUNTER_MBOX_MSG_CACHE.increment(cacheHit ? 100 : 0);
        
        return cnode.mMessage;
    }

    private static InputStream fetchFromStore(MailItem item) throws ServiceException, IOException {
        ZimbraLog.cache.debug("msgcache: fetchFromStore(): id=%d, size=%d, digest=%s.", item.getId(), item.getSize(), item.getDigest());
        MailboxBlob msgBlob = item.getBlob();
        if (msgBlob == null)
            throw ServiceException.FAILURE("missing blob for id: " + item.getId() + ", change: " + item.getModifiedSequence(), null);
        return StoreManager.getInstance().getContent(msgBlob);
    }

    /**
     * Public API that adds a <tt>MimeMessage</tt> that's streamed from disk to the <tt>MessageCache</tt>.
     * The message being cached cannot require MIME expansion.
     * @param digest the message digest
     * @param msg a <tt>MimeMessage</tt> that is being streamed from disk
     */
    public static void cacheStreamedMessage(String digest, MimeMessage msg) {
        ZimbraLog.cache.debug("msgcache: cacheStreamedMessage(): digest=%s", digest);
        // Remove/update size, in case an older version is already in the cache
        purge(digest);
        CacheNode cnode = new CacheNode(STREAMED_MESSAGE_SIZE, msg, ConvertedState.BOTH);
        cacheItem(digest, cnode);
    }

    private static void cacheItem(String key, CacheNode cnode) {
        if (cnode.mSize >= mMaxCacheSize) {
            ZimbraLog.cache.debug("msgcache: not caching %s.  size %d is bigger than max cache size %d.", key, cnode.mSize, mMaxCacheSize);
            return;
        }
        
        synchronized (mCache) {
            mCache.put(key, cnode);
            mTotalSize += cnode.mSize;
            ZimbraLog.cache.debug("msgcache: caching %s message: size=%d, digest=%s.  Cache size is now %d.",
                (cnode.mContent != null ? "raw" : "mime"), cnode.mSize, key, mTotalSize);

            // trim the cache if needed
            if (mTotalSize > mMaxCacheSize) {
                ZimbraLog.cache.debug("msgcache: cache size %d exceeded maximum %d.", mTotalSize, mMaxCacheSize);
                for (Iterator<Map.Entry<String, CacheNode>> it = mCache.entrySet().iterator(); mTotalSize > mMaxCacheSize && it.hasNext(); ) {
                    Map.Entry<String, CacheNode> entry = it.next();
                    String digest = entry.getKey();
                    CacheNode cnPurge = entry.getValue();
                    it.remove();
                    mTotalSize -= cnPurge.mSize;
                    ZimbraLog.cache.debug("msgcache: removed %s, size %d.  Cache size is now %d.", digest, cnPurge.mSize, mTotalSize);
                }
            }
        }
    }
}
