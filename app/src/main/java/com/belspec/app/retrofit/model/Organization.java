package com.belspec.app.retrofit.model;

import org.simpleframework.xml.Element;


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
