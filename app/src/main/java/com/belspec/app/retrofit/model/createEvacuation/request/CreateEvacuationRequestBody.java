package com.belspec.app.retrofit.model.createEvacuation.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Body")
public class CreateEvacuationRequestBody {
    @Element(name = "tes:CreateEvacuation")
    private CreateEvacuationRequestData data;

    public CreateEvacuationRequestBody(String manufacture, String model, String carId,
                                       String color, String photo1, String photo2,
                                       String photo3, String photo4, String address,
                                       String clause, String policeDepartment, String policeman,
                                       String wrecker, String organization, String regDevice, String code,
                                       String witness1Name, String witness1Address, String witness1Contact, String witness1Signature, String plea1,
                                       String witness2Name, String witness2Address, String witness2Contact, String witness2Signature, String plea2,
                                       String policemanSignature, String revisionResult, boolean withoutEvacuation, String parking, String roadLawPoints){
        this.data = new CreateEvacuationRequestData(manufacture, model, carId, color, photo1, photo2, photo3, photo4, address,
                clause, policeDepartment, policeman, wrecker, organization, regDevice, code,
                witness1Name, witness1Address, witness1Contact, witness1Signature, plea1, witness2Name, witness2Address, witness2Contact, witness2Signature,
                policemanSignature, plea2, revisionResult, withoutEvacuation, parking, roadLawPoints);
    }
}
