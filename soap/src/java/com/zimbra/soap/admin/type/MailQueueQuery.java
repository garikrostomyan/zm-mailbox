/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013, 2014, 2016 Synacor, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */

package com.zimbra.soap.admin.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import com.zimbra.soap.type.ZmBoolean;

import com.zimbra.common.soap.AdminConstants;
@XmlAccessorType(XmlAccessType.NONE)
public class MailQueueQuery {

    /**
     * @zm-api-field-tag queue-name
     * @zm-api-field-description Queue name
     */
    @XmlAttribute(name=AdminConstants.A_NAME /* name */, required=true)
    private final String queueName;

    /**
     * @zm-api-field-tag do-scan-flag
     * @zm-api-field-description To fora a queue scan, set this to <b>1 (true)</b>
     */
    @XmlAttribute(name=AdminConstants.A_SCAN /* scan */, required=false)
    private final ZmBoolean scan;

    /**
     * @zm-api-field-tag max-wait-seconds
     * @zm-api-field-description Maximum time to wait for the scan to complete in seconds (default 3)
     */
    @XmlAttribute(name=AdminConstants.A_WAIT /* wait */, required=false)
    private final Long waitSeconds;

    /**
     * @zm-api-field-description Query
     */
    @XmlElement(name=AdminConstants.E_QUERY /* query */, required=true)
    private final QueueQuery query;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private MailQueueQuery() {
        this((String) null, (Boolean) null, (Long) null, (QueueQuery) null);
    }

    public MailQueueQuery(String queueName, Boolean scan, Long waitSeconds, QueueQuery query) {
        this.queueName = queueName;
        this.scan = ZmBoolean.fromBool(scan);
        this.waitSeconds = waitSeconds;
        this.query = query;
    }

    public String getQueueName() { return queueName; }
    public Boolean getScan() { return ZmBoolean.toBool(scan); }
    public Long getWaitSeconds() { return waitSeconds; }
    public QueueQuery getQuery() { return query; }
}
