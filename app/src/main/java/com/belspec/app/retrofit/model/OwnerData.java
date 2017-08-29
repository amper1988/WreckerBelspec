package com.belspec.app.retrofit.model;

import org.simpleframework.xml.Element;

public class OwnerData {
    @Element(name = "OwnerName", required = false)
    private String ownerName;
    @Element(name = "OwnerAddress", required = false)
    private String ownerAddress;
    @Element(name = "TechCertSeries", required = false)
    private String techCertSeries;
    @Element(name = "TechCertNumber", required = false)
    private String techCertNumber;

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public String getTechCertSeries() {
        return techCertSeries;
    }

    public String getTechCertNumber() {
        return techCertNumber;
    }
}
