package com.belspec.app.retrofit.model.getOwnerData.response;

import com.belspec.app.retrofit.model.OwnerDataList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class GetOwnerDataResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetOwnerDataResponse")
    private OwnerDataList body;

    public OwnerDataList getBody() {
        return body;
    }
}
