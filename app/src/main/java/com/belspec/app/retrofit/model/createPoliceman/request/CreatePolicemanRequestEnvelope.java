package com.belspec.app.retrofit.model.createPoliceman.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class CreatePolicemanRequestEnvelope {
    @Element(name = "soap12:Body")
    private CreatePolicemanRequestBody body;

    public CreatePolicemanRequestEnvelope(String name, String policeDepartment, String rank, String position, String code){
        this.body = new CreatePolicemanRequestBody(name, policeDepartment, rank, position, code);
    }
}
