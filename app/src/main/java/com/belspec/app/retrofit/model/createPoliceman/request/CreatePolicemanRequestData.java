package com.belspec.app.retrofit.model.createPoliceman.request;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tes:CreatePoliceman")
public class CreatePolicemanRequestData {
    @Element(name = "tes:PoliceDepartment")
    private String policeDepartment;
    @Element(name = "tes:Rank")
    private String rank;
    @Element(name = "tes:Position")
    private String position;
    @Element(name = "tes:Code")
    private String code;
    @Element(name = "tes:Name")
    private String name;

    public CreatePolicemanRequestData(){
        this.code = "";
        this.policeDepartment = "";
        this.position = "";
        this.rank = "";
        this.name = "";
    }

    public CreatePolicemanRequestData(String name, String policeDepartment, String rank, String position, String code){
        this.code = code;
        this.policeDepartment = policeDepartment;
        this.rank = rank;
        this.position = position;
        this.name = name;
    }

    public void setPoliceDepartment(String policeDepartment) {
        this.policeDepartment = policeDepartment;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name){
        this.name = name;
    }
}
