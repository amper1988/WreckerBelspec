package com.belspec.app.retrofit.model.getDefaultData.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class GetDefaultDataResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetDefaultDataResponse")
    private DefaultData body;

    public DefaultData getBody(){
        return this.body;
    }
}
