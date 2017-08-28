package com.belspec.app.retrofit.model.getRanks.request;

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
public class GetRanksRequestEnvelope {
    @Element(name = "tes:GetRanks")
    @Path("soap12:Body")
    public String body;

    public GetRanksRequestEnvelope(){
        this.body = "";
    }
}
