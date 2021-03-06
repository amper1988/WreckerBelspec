package com.belspec.app.retrofit.model.checkUpdate.response;

import com.belspec.app.retrofit.model.RoadLawPointItem;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class CheckUpdateResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/CheckUpdateResponse")
    private CheckUpdateResponseData body;

    public CheckUpdateResponseData getBody(){
        return body;
    }
}
