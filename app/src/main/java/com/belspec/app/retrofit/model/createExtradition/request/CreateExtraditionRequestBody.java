package com.belspec.app.retrofit.model.createExtradition.request;

import com.belspec.app.retrofit.model.createEvacuation.request.CreateEvacuationRequestData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Body")
public class CreateExtraditionRequestBody {
    @Element(name = "tes:Extradition")
    private CreateExtraditionRequestData data;

    public CreateExtraditionRequestBody(int docId, String seriesRc, String numberRc, String lastNameRc, String addressRc,
                                        String seriesDl, String numberDl, String lastNameDl, String addressDl, String contact,
                                        String code, int userType, String policeman){
        this.data = new CreateExtraditionRequestData(docId,seriesRc, numberRc,lastNameRc, addressRc,
                seriesDl,numberDl,lastNameDl, addressDl, contact,
                code,userType, policeman);
    }
}
