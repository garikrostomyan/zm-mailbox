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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.type.AttributeSelectorImpl;
import com.zimbra.soap.admin.type.CalendarResourceSelector;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name=AdminConstants.E_GET_CALENDAR_RESOURCE_REQUEST)
public class GetCalendarResourceRequest extends AttributeSelectorImpl {

    @XmlAttribute(name=AdminConstants.A_APPLY_COS, required=false)
    private Boolean applyCos;
    @XmlElement(name=AdminConstants.E_CALENDAR_RESOURCE)
    private CalendarResourceSelector calResource;

    public GetCalendarResourceRequest() {
        this((CalendarResourceSelector) null, (Boolean) null);
    }

    public GetCalendarResourceRequest(
            CalendarResourceSelector calResource) { 
        this(calResource, (Boolean) null, (Iterable<String>) null);
    }

    public GetCalendarResourceRequest(
            CalendarResourceSelector calResource,
            Boolean applyCos) { 
        this(calResource, applyCos, (Iterable<String>) null);
    }

    public GetCalendarResourceRequest(
            CalendarResourceSelector calResource,
            Boolean applyCos,
            Iterable<String> attrs) {
        super(attrs);
        setApplyCos(applyCos);
        setCalResource(calResource);
    }

    public void setApplyCos(Boolean applyCos) { this.applyCos = applyCos; }
    public void setCalResource(CalendarResourceSelector calResource) {
        this.calResource = calResource;
    }

    public Boolean getApplyCos() { return applyCos; }
    public CalendarResourceSelector getCalResource() { return calResource; }
}
