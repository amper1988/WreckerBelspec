package com.belspec.app.retrofit.model.getCarOnEvacuation.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tes:GetCarOnEvacuation")
public class GetCarOnEvacuationRequestData {
    @Element(name = "tes:UserType")
    private int userType;

    @Element(name = "tes:PoliceDepartment")
    private String policeDepartment;

    @Element(name = "tes:Policeman")
    private String policeman;

    @Element(name = "tes:Organization")
    private String organization;

    @Element(name = "tes:Wrecker")
    private String wrecker;

    @Element(name = "tes:CarID")
    private String carId;

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public GetCarOnEvacuationRequestData(int userType, String policeDepartment, String policeman, String organization, String wrecker, String carId) {
        this.userType = userType;
        this.policeDepartment = policeDepartment;
        this.policeman = policeman;
        this.organization = organization;
        this.wrecker = wrecker;
        this.carId = carId;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setPoliceDepartment(String policeDepartment) {
        this.policeDepartment = policeDepartment;
    }

    public void setPoliceman(String policeman) {
        this.policeman = policeman;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setWrecker(String wrecker) {
        this.wrecker = wrecker;
    }
}
