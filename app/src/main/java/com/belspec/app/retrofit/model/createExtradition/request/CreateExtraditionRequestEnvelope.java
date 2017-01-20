package com.belspec.app.retrofit.model.createExtradition.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class CreateExtraditionRequestEnvelope {
    @Element(name = "soap12:Body")
    private CreateExtraditionRequestBody body;

    public CreateExtraditionRequestEnvelope(int docId, String seriesRc, String numberRc, String lastNameRc, String addressRc,
                                            String seriesDl, String numberDl, String lastNameDl, String addressDl, String contact,
                                            String code, int userType, String policeman, boolean printPhoto){
        body = new CreateExtraditionRequestBody(docId, seriesRc, numberRc, lastNameRc, addressRc, seriesDl, numberDl, lastNameDl, addressDl, contact, code,userType,policeman, printPhoto);
    }
}
