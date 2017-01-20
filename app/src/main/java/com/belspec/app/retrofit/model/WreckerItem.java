package com.belspec.app.retrofit.model;

import com.belspec.app.retrofit.model.Wrecker;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class WreckerItem {
    @ElementList(entry = "WreckerItem", inline = true)
    private List<Wrecker> wreckerList;

    public List<Wrecker> getWreckerList() {
        return wreckerList;
    }
}
