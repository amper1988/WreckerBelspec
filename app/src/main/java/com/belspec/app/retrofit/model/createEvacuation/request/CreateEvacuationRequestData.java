package com.belspec.app.retrofit.model.createEvacuation.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.filter.StackFilter;

@Root(name = "tes:Evacuation")
public class CreateEvacuationRequestData {
    @Element(name = "tes:Manufacture")
    private String manufacture;
    @Element(name = "tes:Model")
    private String model;
    @Element(name = "tes:CarID")
    private String carId;
    @Element(name = "tes:Color")
    private String color;
    @Element(name = "tes:Photo1")
    private String photo1;
    @Element(name = "tes:Photo2")
    private String photo2;
    @Element(name = "tes:Photo3")
    private String photo3;
    @Element(name = "tes:Photo4")
    private String photo4;
    @Element(name = "tes:Address")
    private String address;
    @Element(name = "tes:Clause")
    private String clause;
    @Element(name = "tes:PoliceDepartment")
    private String policeDepartment;
    @Element(name = "tes:Policeman")
    private String policeman;
    @Element(name = "tes:Wrecker")
    private String wrecker;
    @Element(name = "tes:Organization")
    private String organization;
    @Element(name = "tes:EvacuationType")
    private int evacuationType;
    @Element(name = "RegDevice")
    private String regDevice;
    @Element(name = "tes:UserType")
    private int userType;
    @Element(name = "tes:Code")
    private String code;
    @Element (name = "tes:Witness1Name")
    private String witness1Name;
    @Element(name = "tes:Witness2Name")
    private String witness2Name;
    @Element(name = "tes:Witness1Address")
    private String witness1Address;
    @Element(name = "tes:Witness2Address")
    private String witness2Address;
    @Element(name = "tes:Witness1Contact")
    private String witness1Contact;
    @Element(name = "tes:Witness2Contact")
    private String witness2Contact;
    @Element(name = "tes:Witness1Signature")
    private String witness1Signature;
    @Element(name = "tes:Witness2Signature")
    private String witness2Signature;
    @Element(name = "tes:PolicemanSignature")
    private String policemanSignature;
    @Element(name = "tes:RevisionResult")
    private String revisionResult;
    @Element(name = "tes:Plea1")
    private String plea1;
    @Element(name = "tes:Plea2")
    private String plea2;

    public void setPlea2(String plea2) {
        this.plea2 = plea2;
    }

    public void setPlea1(String plea1) {
        this.plea1 = plea1;
    }
    public void setWitness1Name(String witness1Name) {
        this.witness1Name = witness1Name;
    }

    public void setWitness2Name(String witness2Name) {
        this.witness2Name = witness2Name;
    }

    public void setWitness1Address(String witness1Address) {
        this.witness1Address = witness1Address;
    }

    public void setWitness2Address(String witness2Address) {
        this.witness2Address = witness2Address;
    }

    public void setWitness1Contact(String witness1Contact) {
        this.witness1Contact = witness1Contact;
    }

    public void setWitness2Contact(String witness2Contact) {
        this.witness2Contact = witness2Contact;
    }

    public void setWitness1Signature(String witness1Signature) {
        this.witness1Signature = witness1Signature;
    }


    public void setWitness2Signature(String witness2Signature) {
        this.witness2Signature = witness2Signature;
    }

    public void setPolicemanSignature(String policemanSignature) {
        this.policemanSignature = policemanSignature;
    }

     public void setRevisionResult(String revisionResult) {
        this.revisionResult = revisionResult;
    }

    public CreateEvacuationRequestData setManufacture(String manufacture) {
        this.manufacture = manufacture;
        return this;
    }

    public CreateEvacuationRequestData setModel(String model) {
        this.model = model;
        return this;
    }

    public CreateEvacuationRequestData setCarId(String carId) {
        this.carId = carId;
        return this;
    }

    public CreateEvacuationRequestData setColor(String color) {
        this.color = color;
        return this;
    }

    public CreateEvacuationRequestData setPhoto1(String photo1) {
        this.photo1 = photo1;
        return this;
    }

    public CreateEvacuationRequestData setPhoto2(String photo2) {
        this.photo2 = photo2;
        return this;
    }

    public CreateEvacuationRequestData setPhoto3(String photo3) {
        this.photo3 = photo3;
        return this;
    }

    public CreateEvacuationRequestData setPhoto4(String photo4) {
        this.photo4 = photo4;
        return this;
    }

    public CreateEvacuationRequestData setAddress(String address) {
        this.address = address;
        return this;
    }

    public CreateEvacuationRequestData setClause(String clause) {
        this.clause = clause;
        return this;
    }

    public CreateEvacuationRequestData setPoliceDepartment(String policeDepartment) {
        this.policeDepartment = policeDepartment;
        return this;
    }

    public CreateEvacuationRequestData setPoliceman(String policeman) {
        this.policeman = policeman;
        return this;
    }

    public CreateEvacuationRequestData setWrecker(String wrecker) {
        this.wrecker = wrecker;
        return this;
    }

    public CreateEvacuationRequestData setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public CreateEvacuationRequestData setEvacuationType(int evacuationType) {
        this.evacuationType = evacuationType;
        return this;
    }

    public CreateEvacuationRequestData setUserType(int userType) {
        this.userType = userType;
        return this;
    }

    public CreateEvacuationRequestData setRegDevice(String regDevice){
        this.regDevice = regDevice;
        return this;
    }

    public CreateEvacuationRequestData setCode(String code){
        this.code = code;
        return this;
    }

    public CreateEvacuationRequestData(){
        super();
        this.manufacture = "";
        this.model = "";
        this.carId = "";
        this.color = "";
        this.photo1 = "";
        this.photo2 = "";
        this.photo3 = "";
        this.photo4 = "";
        this.address = "";
        this.clause = "";
        this.policeDepartment = "";
        this.policeman = "";
        this.wrecker = "";
        this.organization = "";
        this.evacuationType = 0;

        this.userType = 0;
        this.regDevice = "";
        this.code = "";
        this.witness1Name = "";
        this.witness1Address = "";
        this.witness1Contact = "";
        this.witness1Signature = "";

        this.witness2Name = "";
        this.witness2Address = "";
        this.witness2Contact = "";
        this.witness2Signature = "";

        this.policemanSignature = "";
        this.revisionResult = "";
        this.plea1 ="";
        this.plea2 = "";
    }

    public CreateEvacuationRequestData(String manufacture, String model, String carId,
                                       String color, String photo1, String photo2,
                                       String photo3, String photo4, String address,
                                       String clause, String policeDepartment, String policeman,
                                       String wrecker, String organization, int evacuationType, int userType, String regDevice, String code,
                                       String witness1Name, String witness1Address, String witness1Contact, String witness1Signature, String plea1,
                                       String witness2Name, String witness2Address, String witness2Contact, String witness2Signature, String plea2,
                                       String policemanSignature, String revisionResult){
        this.manufacture = manufacture;
        this.model = model;
        this.carId = carId;
        this.color = color;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.photo4 = photo4;
        this.address = address;
        this.clause = clause;
        this.policeDepartment = policeDepartment;
        this.policeman = policeman;
        this.wrecker = wrecker;
        this.organization = organization;
        this.evacuationType = evacuationType;
        this.regDevice = regDevice;

        this.userType = userType;
        this.code = code;

        this.witness1Name = witness1Name;
        this.witness1Address = witness1Address;
        this.witness1Contact = witness1Contact;
        this.witness1Signature = witness1Signature;

        this.witness2Name = witness2Name;
        this.witness2Address = witness2Address;
        this.witness2Contact = witness2Contact;
        this.witness2Signature = witness2Signature;

        this.policemanSignature = policemanSignature;
        this.revisionResult = revisionResult;

        this.plea1 = plea1;
        this.plea2 = plea2;
    }
}
