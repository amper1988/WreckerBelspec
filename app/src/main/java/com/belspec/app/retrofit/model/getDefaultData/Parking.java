package com.belspec.app.retrofit.model.getDefaultData;

import org.simpleframework.xml.Element;


public class Parking {
    @Element(name = "Name", required = false)
    private String name;

    public String getName() {
        return name;
    }
}
