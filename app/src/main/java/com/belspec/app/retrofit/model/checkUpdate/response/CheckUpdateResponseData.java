package com.belspec.app.retrofit.model.checkUpdate.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return")
public class CheckUpdateResponseData {
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
