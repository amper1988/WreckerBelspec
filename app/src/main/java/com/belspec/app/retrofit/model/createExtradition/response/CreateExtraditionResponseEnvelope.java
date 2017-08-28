package com.belspec.app.retrofit.model.createExtradition.response;


import com.belspec.app.retrofit.model.createEvacuation.response.CreateEvacuationResponseData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class CreateExtraditionResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/CreateExtraditionResponse")
    private CreateEvacuationResponseData data;

    public CreateEvacuationResponseData getData() {
        return data;
    }

}
