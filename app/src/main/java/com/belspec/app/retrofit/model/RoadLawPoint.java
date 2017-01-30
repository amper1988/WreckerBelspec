package com.belspec.app.retrofit.model;


import org.simpleframework.xml.Element;

public class RoadLawPoint {
    @Element(name = "Name")
    private String name;

    public String getName(){
        return this.name;
    }
}
