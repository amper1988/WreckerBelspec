package com.belspec.app.retrofit.model.getDriverData.response;

import com.belspec.app.retrofit.model.DriverDataList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class GetDriverDataResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetDriverDataResponse")
    private DriverDataList body;

    public DriverDataList getBody() {
        return body;
    }
}
