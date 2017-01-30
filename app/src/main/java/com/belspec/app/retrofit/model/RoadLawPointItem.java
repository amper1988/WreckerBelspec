package com.belspec.app.retrofit.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "RoadLawPointList")
public class RoadLawPointItem {
    @ElementList(entry = "RoadLawPoint", inline = true)
    private List<RoadLawPoint> roadLawPoints;

    public List<RoadLawPoint> getRoadLawPointList() {
        return roadLawPoints;
    }
}
