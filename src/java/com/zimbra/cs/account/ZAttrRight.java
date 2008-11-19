/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007 Zimbra, Inc.
 * 
 * The contents of this file are subject to the Yahoo! Public License
 * Version 1.0 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */

/*
 * Created on Nov 17, 2008
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.zimbra.cs.account;

import com.zimbra.common.util.DateUtil;
import com.zimbra.common.util.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * AUTO-GENERATED. DO NOT EDIT.
 *
 */
public class ZAttrRight extends NamedEntry {

    public ZAttrRight(String name, String id, Map<String,Object> attrs, Provisioning prov) {
        super(name, id, attrs, null, prov);
    }

    ///// BEGIN-AUTO-GEN-REPLACE

    /* build: 5.0 pshao 20081119-1050 */

    /**
     * RFC2256: common name(s) for which the entity is known by
     *
     * @return cn, or null unset
     */
    @ZAttr(id=-1)
    public String getCn() {
        return getAttr(Provisioning.A_cn);
    }

    /**
     * RFC2256: common name(s) for which the entity is known by
     *
     * @param cn new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> setCn(String cn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_cn, cn);
        return attrs;
    }

    /**
     * RFC2256: common name(s) for which the entity is known by
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> unsetCn(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_cn, "");
        return attrs;
    }

    /**
     * RFC2256: descriptive information
     *
     * @return description, or null unset
     */
    @ZAttr(id=-1)
    public String getDescription() {
        return getAttr(Provisioning.A_description);
    }

    /**
     * RFC2256: descriptive information
     *
     * @param description new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> setDescription(String description, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_description, description);
        return attrs;
    }

    /**
     * RFC2256: descriptive information
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> unsetDescription(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_description, "");
        return attrs;
    }

    /**
     * Zimbra access control list
     *
     * @return zimbraACE, or ampty array if unset
     */
    @ZAttr(id=659)
    public String[] getACE() {
        return getMultiAttr(Provisioning.A_zimbraACE);
    }

    /**
     * Zimbra access control list
     *
     * @param zimbraACE new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=659)
    public Map<String,Object> setACE(String[] zimbraACE, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraACE, zimbraACE);
        return attrs;
    }

    /**
     * Zimbra access control list
     *
     * @param zimbraACE new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=659)
    public Map<String,Object> addACE(String zimbraACE, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraACE, zimbraACE);
        return attrs;
    }

    /**
     * Zimbra access control list
     *
     * @param zimbraACE existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=659)
    public Map<String,Object> removeACE(String zimbraACE, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraACE, zimbraACE);
        return attrs;
    }

    /**
     * Zimbra access control list
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=659)
    public Map<String,Object> unsetACE(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraACE, "");
        return attrs;
    }

    /**
     * Zimbra Systems Unique ID
     *
     * @return zimbraId, or null unset
     */
    @ZAttr(id=1)
    public String getId() {
        return getAttr(Provisioning.A_zimbraId);
    }

    /**
     * Zimbra Systems Unique ID
     *
     * @param zimbraId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=1)
    public Map<String,Object> setId(String zimbraId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraId, zimbraId);
        return attrs;
    }

    /**
     * Zimbra Systems Unique ID
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=1)
    public Map<String,Object> unsetId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraId, "");
        return attrs;
    }

    /**
     * attributes contained in a getAttrs or setAttrs right
     *
     * @return zimbraRightAttrs, or ampty array if unset
     *
     * @since ZCS future
     */
    @ZAttr(id=767)
    public String[] getRightAttrs() {
        return getMultiAttr(Provisioning.A_zimbraRightAttrs);
    }

    /**
     * attributes contained in a getAttrs or setAttrs right
     *
     * @param zimbraRightAttrs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=767)
    public Map<String,Object> setRightAttrs(String[] zimbraRightAttrs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRightAttrs, zimbraRightAttrs);
        return attrs;
    }

    /**
     * attributes contained in a getAttrs or setAttrs right
     *
     * @param zimbraRightAttrs new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=767)
    public Map<String,Object> addRightAttrs(String zimbraRightAttrs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraRightAttrs, zimbraRightAttrs);
        return attrs;
    }

    /**
     * attributes contained in a getAttrs or setAttrs right
     *
     * @param zimbraRightAttrs existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=767)
    public Map<String,Object> removeRightAttrs(String zimbraRightAttrs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraRightAttrs, zimbraRightAttrs);
        return attrs;
    }

    /**
     * attributes contained in a getAttrs or setAttrs right
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=767)
    public Map<String,Object> unsetRightAttrs(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRightAttrs, "");
        return attrs;
    }

    /**
     * rights contained in a combo right
     *
     * @return zimbraRightRights, or ampty array if unset
     *
     * @since ZCS future
     */
    @ZAttr(id=766)
    public String[] getRightRights() {
        return getMultiAttr(Provisioning.A_zimbraRightRights);
    }

    /**
     * rights contained in a combo right
     *
     * @param zimbraRightRights new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=766)
    public Map<String,Object> setRightRights(String[] zimbraRightRights, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRightRights, zimbraRightRights);
        return attrs;
    }

    /**
     * rights contained in a combo right
     *
     * @param zimbraRightRights new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=766)
    public Map<String,Object> addRightRights(String zimbraRightRights, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraRightRights, zimbraRightRights);
        return attrs;
    }

    /**
     * rights contained in a combo right
     *
     * @param zimbraRightRights existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=766)
    public Map<String,Object> removeRightRights(String zimbraRightRights, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraRightRights, zimbraRightRights);
        return attrs;
    }

    /**
     * rights contained in a combo right
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=766)
    public Map<String,Object> unsetRightRights(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRightRights, "");
        return attrs;
    }

    /**
     * right target type
     *
     * <p>Valid values: [right, zimlet, cos, config, distributionlist, account, domain, xmppcomponent, server, resource, global]
     *
     * @return zimbraRightTargetType, or null if unset and/or has invalid value
     *
     * @since ZCS future
     */
    @ZAttr(id=765)
    public ZAttrProvisioning.RightTargetType getRightTargetType() {
        try { String v = getAttr(Provisioning.A_zimbraRightTargetType); return v == null ? null : ZAttrProvisioning.RightTargetType.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return null; }
    }

    /**
     * right target type
     *
     * <p>Valid values: [right, zimlet, cos, config, distributionlist, account, domain, xmppcomponent, server, resource, global]
     *
     * @return zimbraRightTargetType, or ampty array if unset
     *
     * @since ZCS future
     */
    @ZAttr(id=765)
    public String[] getRightTargetTypeAsString() {
        return getMultiAttr(Provisioning.A_zimbraRightTargetType);
    }

    /**
     * right target type
     *
     * <p>Valid values: [right, zimlet, cos, config, distributionlist, account, domain, xmppcomponent, server, resource, global]
     *
     * @param zimbraRightTargetType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=765)
    public Map<String,Object> setRightTargetType(ZAttrProvisioning.RightTargetType zimbraRightTargetType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRightTargetType, zimbraRightTargetType.toString());
        return attrs;
    }

    /**
     * right target type
     *
     * <p>Valid values: [right, zimlet, cos, config, distributionlist, account, domain, xmppcomponent, server, resource, global]
     *
     * @param zimbraRightTargetType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=765)
    public Map<String,Object> setRightTargetTypeAsString(String[] zimbraRightTargetType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRightTargetType, zimbraRightTargetType);
        return attrs;
    }

    /**
     * right target type
     *
     * <p>Valid values: [right, zimlet, cos, config, distributionlist, account, domain, xmppcomponent, server, resource, global]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=765)
    public Map<String,Object> unsetRightTargetType(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRightTargetType, "");
        return attrs;
    }

    /**
     * right type
     *
     * <p>Valid values: [setAttrs, getAttrs, combo, preset]
     *
     * @return zimbraRightType, or null if unset and/or has invalid value
     *
     * @since ZCS future
     */
    @ZAttr(id=764)
    public ZAttrProvisioning.RightType getRightType() {
        try { String v = getAttr(Provisioning.A_zimbraRightType); return v == null ? null : ZAttrProvisioning.RightType.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return null; }
    }

    /**
     * right type
     *
     * <p>Valid values: [setAttrs, getAttrs, combo, preset]
     *
     * @return zimbraRightType, or null unset
     *
     * @since ZCS future
     */
    @ZAttr(id=764)
    public String getRightTypeAsString() {
        return getAttr(Provisioning.A_zimbraRightType);
    }

    /**
     * right type
     *
     * <p>Valid values: [setAttrs, getAttrs, combo, preset]
     *
     * @param zimbraRightType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=764)
    public Map<String,Object> setRightType(ZAttrProvisioning.RightType zimbraRightType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRightType, zimbraRightType.toString());
        return attrs;
    }

    /**
     * right type
     *
     * <p>Valid values: [setAttrs, getAttrs, combo, preset]
     *
     * @param zimbraRightType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=764)
    public Map<String,Object> setRightTypeAsString(String zimbraRightType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRightType, zimbraRightType);
        return attrs;
    }

    /**
     * right type
     *
     * <p>Valid values: [setAttrs, getAttrs, combo, preset]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS future
     */
    @ZAttr(id=764)
    public Map<String,Object> unsetRightType(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRightType, "");
        return attrs;
    }

    ///// END-AUTO-GEN-REPLACE

}
