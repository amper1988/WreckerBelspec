package com.belspec.app.retrofit.model.createPoliceman.response;


import com.belspec.app.retrofit.model.createEvacuation.response.CreateEvacuationResponseData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class CreatePolicemanResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/CreatePolicemanResponse")
    private CreateEvacuationResponseData data;

    public CreateEvacuationResponseData getData(){
        return this.data;
    }
}
