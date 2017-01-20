package com.belspec.app.retrofit.model.getPoliceDepartment.response;

import com.belspec.app.retrofit.model.PoliceDepartment;
import com.belspec.app.retrofit.model.getDefaultData.response.DefaultData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class GetPoliceDepartmentResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetPoliceDepartmentResponse")
    private PoliceDepartment body;

    public PoliceDepartment getBody(){
        return this.body;
    }
}
