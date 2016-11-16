package com.belspec.app.retrofit.model.createEvacuation.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return")
public class CreateEvacuationResponseData {
    @Element(name = "Code")
    private int code;

    @Element(name = "Description")
    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription(){
        return description;
    }
}
