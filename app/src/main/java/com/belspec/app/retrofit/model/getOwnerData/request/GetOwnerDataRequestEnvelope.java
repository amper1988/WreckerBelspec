package com.belspec.app.retrofit.model.getOwnerData.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class GetOwnerDataRequestEnvelope {
    @Element(name = "tes:CarID")
    @Path("soap12:Body/tes:GetOwnerData")
    private String carId;

    public GetOwnerDataRequestEnvelope(String carId) {
        this.carId = carId;
    }
}
