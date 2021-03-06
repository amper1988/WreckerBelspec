package com.belspec.app.retrofit.model.createEvacuation.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class CreateEvacuationResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/CreateEvacuationResponse")
    private CreateEvacuationResponseData data;

    public CreateEvacuationResponseData getData() {
        return data;
    }

}
