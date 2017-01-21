package com.belspec.app.retrofit.model.createPoliceman.request;

import com.belspec.app.retrofit.model.createExtradition.request.CreateExtraditionRequestBody;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Body")
public class CreatePolicemanRequestBody {
    @Element(name = "tes:CreatePoliceman")
    private CreatePolicemanRequestData data;

    public CreatePolicemanRequestBody(String name, String policeDepartment, String rank, String position, String code){
        this.data = new CreatePolicemanRequestData(name ,policeDepartment, rank, position, code);
    }
}
