package com.belspec.app.ui.detection;

import android.graphics.Bitmap;

public class Witness {
    private String name;
    private String address;
    private Bitmap signature;
    private String plea;
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Witness(String name, String address, Bitmap signature, String plea, String contact){
        this.name = name;
        this.address = address;
        this.signature = signature;
        this.plea = plea;
        this.contact = contact;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getSignature() {
        return signature;
    }

    public void setSignature(Bitmap signature) {
        this.signature = signature;
    }

    public String getPlea() {
        return plea;
    }

    public void setPlea(String plea) {
        this.plea = plea;
    }


}
