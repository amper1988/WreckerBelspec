package com.belspec.app.retrofit.model.getCarOnEvacuation.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "Envelope")
public class GetCarOnEvacuationResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "Body")
    private GetCarOnEvacuationResponseBody dataList;

    public GetCarOnEvacuationResponseBody getDataList(){
        return dataList;
    }

}
