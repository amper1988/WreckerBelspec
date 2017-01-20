package com.belspec.app.retrofit.model;

import com.belspec.app.retrofit.model.Manufacture;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ManufactureList")
public class ManufactureItem {
    @ElementList(entry = "ManufactureItem", inline = true)
    private List<Manufacture> manufactureList;

    public List<Manufacture> getManufactureList() {
        return manufactureList;
    }
}
