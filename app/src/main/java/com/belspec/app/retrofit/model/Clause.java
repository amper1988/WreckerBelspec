package com.belspec.app.retrofit.model;

import org.simpleframework.xml.Element;


public class Clause {
    @Element(name = "Name", required = false)
    private String name;

    public String getName() {
        return name;
    }
}
