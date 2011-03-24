/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011 Zimbra, Inc.
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

package com.zimbra.soap.admin.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AdminConstants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name=AdminConstants.E_DUMP_SESSIONS_REQUEST)
public class DumpSessionsRequest {

    @XmlAttribute(name=AdminConstants.A_LIST_SESSIONS, required=false)
    private final Boolean includeAccounts;

    @XmlAttribute(name=AdminConstants.A_GROUP_BY_ACCOUNT, required=false)
    private final Boolean groupByAccount;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private DumpSessionsRequest() {
        this((Boolean) null, (Boolean) null);
    }

    public DumpSessionsRequest(Boolean includeAccounts,
                    Boolean groupByAccount) {
        this.includeAccounts = includeAccounts;
        this.groupByAccount = groupByAccount;
    }

    public Boolean getIncludeAccounts() { return includeAccounts; }
    public Boolean getGroupByAccount() { return groupByAccount; }
}
