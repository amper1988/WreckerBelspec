package com.belspec.app.retrofit.model;

import org.simpleframework.xml.Element;

public class Position {
    @Element(name = "Name")
    private String name;

    public String getName(){
        return name;
    }
}
