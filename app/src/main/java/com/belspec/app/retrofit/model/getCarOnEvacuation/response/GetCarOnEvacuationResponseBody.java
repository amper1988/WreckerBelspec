package com.belspec.app.retrofit.model.getCarOnEvacuation.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Body")
public class GetCarOnEvacuationResponseBody {
    @Element(name = "return")
    @Path("GetCarOnEvacuationResponse")
    private EvacuationDataList data;

    public EvacuationDataList getData() {
        return data;
    }
}
