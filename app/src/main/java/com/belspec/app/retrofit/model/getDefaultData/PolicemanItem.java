package com.belspec.app.retrofit.model.getDefaultData;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

public class PolicemanItem {
    @ElementList(entry = "PolicemanItem", inline = true)
    private List<Policeman> policemanList;

    public List<Policeman> getPolicemanList() {
        return policemanList;
    }
}
