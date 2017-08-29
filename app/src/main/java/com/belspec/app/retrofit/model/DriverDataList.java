package com.belspec.app.retrofit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "return")
public class DriverDataList {
    @Element(name = "Description", required = false)
    private String description;
    @ElementList(entry = "DriverDataItem", inline = true, required = false)
    private List<DriverData> driverDataItems;

    public List<DriverData> getDriverDataItems() {
        return driverDataItems;
    }

    public String getDescription() {
        return description;
    }
}
