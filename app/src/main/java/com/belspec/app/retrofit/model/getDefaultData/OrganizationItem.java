package com.belspec.app.retrofit.model.getDefaultData;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "OrganizationList")
public class OrganizationItem {
    @ElementList(entry = "OrganizationItem", inline = true)
    private List<Organization> organizationList;

    public List<Organization> getOrganizationList() {
        return organizationList;
    }
}
