package com.belspec.app.retrofit.model.getDefaultData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;


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
