package com.belspec.app.retrofit.model;

import org.simpleframework.xml.Element;


public class PoliceDepartment {
    @Element(name = "Name", required = false)
    private String name;

    @Element(name = "PolicemanList", required = false)
    private PolicemanItem policemanItemList;

    public String getName() {
        return name;
    }

    public PolicemanItem getPolicemanItemList(){
        return policemanItemList;
    }
}
