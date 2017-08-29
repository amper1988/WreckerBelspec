package com.belspec.app.retrofit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "return")
public class OwnerDataList {
    @ElementList(entry = "OwnerDataItem", inline = true, required = false)
    private List<OwnerData> ownerDataItems;
    @Element(name = "Description", required = false)
    private String description;

    public String getDescription() {
        return description;
    }

    public List<OwnerData> getOwnerDataItems() {
        return ownerDataItems;
    }
}
