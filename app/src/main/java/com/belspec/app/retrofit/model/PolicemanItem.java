package com.belspec.app.retrofit.model;

import com.belspec.app.retrofit.model.Policeman;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class PolicemanItem {
    @ElementList(entry = "PolicemanItem", inline = true)
    private List<Policeman> policemanList;

    public List<Policeman> getPolicemanList() {
        return policemanList;
    }
}
