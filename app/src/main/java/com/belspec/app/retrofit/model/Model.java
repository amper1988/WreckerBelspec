package com.belspec.app.retrofit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "ModelItem")
public class Model {
    @Element(name = "Name", required = false)
    private String name;

    public String getName() {
        return name;
    }
}
