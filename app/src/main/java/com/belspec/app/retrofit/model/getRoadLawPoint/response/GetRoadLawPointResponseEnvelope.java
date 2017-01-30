package com.belspec.app.retrofit.model.getRoadLawPoint.response;

import com.belspec.app.retrofit.model.RankItem;
import com.belspec.app.retrofit.model.RoadLawPointItem;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class GetRoadLawPointResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetRoadLawPointResponse")
    private RoadLawPointItem body;

    public RoadLawPointItem getBody(){
        return body;
    }
}
