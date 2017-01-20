package com.belspec.app.retrofit.model;


import org.simpleframework.xml.ElementList;

import java.util.List;

public class PositionItem {
    @ElementList(entry = "PositionItem", inline = true)
    private List<Position> positionList;

    public List<Position> getPositionList(){
        return positionList;
    }
}
