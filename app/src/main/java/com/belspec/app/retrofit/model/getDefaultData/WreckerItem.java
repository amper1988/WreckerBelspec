package com.belspec.app.retrofit.model.getDefaultData;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

public class WreckerItem {
    @ElementList(entry = "WreckerItem", inline = true)
    private List<Wrecker> wreckerList;

    public List<Wrecker> getWreckerList() {
        return wreckerList;
    }
}
