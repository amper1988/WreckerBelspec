package com.belspec.app.retrofit.model.createExtradition.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tes:Extradition")
public class CreateExtraditionRequestData {
    @Element(name = "tes:DocID")
    private int docId;
    @Element(name = "tes:SeriesRC")
    private String seriesRc;
    @Element(name = "tes:NumberRC")
    private String numberRc;
    @Element(name = "tes:LastNameRC")
    private String lastNameRc;
    @Element(name = "tes:AddressRC")
    private String addressRc;
    @Element(name = "tes:SeriesDL")
    private String seriesDl;
    @Element(name = "tes:NumberDL")
    private String numberDl;
    @Element(name = "tes:LastNameDL")
    private String lastNameDl;
    @Element(name = "tes:AddressDL")
    private String addressDl;
    @Element(name = "tes:Contact")
    private String contact;
    @Element(name = "tes:Code")
    private String code;
    @Element(name = "tes:UserType")
    private int userType;
    @Element(name = "tes:Policeman")
    private String policeman;

    public CreateExtraditionRequestData(){
        this.docId = 0;
        this.seriesRc = "";
        this.numberRc = "";
        this.lastNameRc = "";
        this.addressRc = "";
        this.seriesDl = "";
        this.numberDl = "";
        this.lastNameDl = "";
        this.addressDl = "";
        this.contact = "";
        this.code = "";
        this.userType = 0;
        this.policeman = "";
    }
    public CreateExtraditionRequestData(int docId, String seriesRc, String numberRc, String lastNameRc, String addressRc,
                                        String seriesDl, String numberDl, String lastNameDl, String addressDl, String contact,
                                        String code, int userType, String policeman){
        this.docId = docId;
        this.seriesRc = seriesRc;
        this.numberRc = numberRc;
        this.lastNameRc = lastNameRc;
        this.addressRc = addressRc;
        this.seriesDl = seriesDl;
        this.numberDl = numberDl;
        this.lastNameDl = lastNameDl;
        this.addressDl = addressDl;
        this.contact = contact;
        this.code = code;
        this.userType = userType;
        this.policeman = policeman;

    }
    public void setDocId(int docId) {
        this.docId = docId;
    }

    public void setSeriesRc(String seriesRc) {
        this.seriesRc = seriesRc;
    }

    public void setNumberRc(String numberRc) {
        this.numberRc = numberRc;
    }

    public void setLastNameRc(String lastNameRc) {
        this.lastNameRc = lastNameRc;
    }

    public void setAddressRc(String addressRc) {
        this.addressRc = addressRc;
    }

    public void setSeriesDl(String seriesDl) {
        this.seriesDl = seriesDl;
    }

    public void setNumberDl(String numberDl) {
        this.numberDl = numberDl;
    }

    public void setLastNameDl(String lastNameDl) {
        this.lastNameDl = lastNameDl;
    }

    public void setAddressDl(String addressDl) {
        this.addressDl = addressDl;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setPoliceman(String policeman) {
        this.policeman = policeman;
    }
}
