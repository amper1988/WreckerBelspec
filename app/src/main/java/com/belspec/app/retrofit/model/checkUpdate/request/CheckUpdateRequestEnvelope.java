package com.belspec.app.retrofit.model.checkUpdate.request;

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
public class CheckUpdateRequestEnvelope {
    @Element(name = "tes:Version")
    @Path("soap12:Body/tes:CheckUpdate")
    public String version;

    public CheckUpdateRequestEnvelope(String version){
        this.version = version;
    }
}
