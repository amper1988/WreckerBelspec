package com.belspec.app.retrofit.model.getDefaultData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;


public class Organization {
    @Element(name = "Name", required = false)
    private String name;

    @Element(name = "WreckerList", required = false)
    private WreckerItem wreckerItemList;

    public String getName() {
        return name;
    }

    public WreckerItem getWreckerItemList(){
        return wreckerItemList;
    }
}
