package com.belspec.app.retrofit.model.getCarOnEvacuation.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.StringTokenizer;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class GetCarOnEvacuationRequestEnvelope {


    @Element(name = "tes:GetCarOnEvacuation")
    @Path("soap12:Body")
    private GetCarOnEvacuationRequestData data;

    public GetCarOnEvacuationRequestEnvelope(int userType, String policeDepartment, String policeman, String organizaton, String wrecker, String carId) {
        this.data = new GetCarOnEvacuationRequestData(userType, policeDepartment, policeman, organizaton, wrecker, carId);
    }
}
