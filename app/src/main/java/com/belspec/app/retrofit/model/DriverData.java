package com.belspec.app.retrofit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "DriverDataItem")
public class DriverData {
    @Element(name = "DriverName", required = false)
    private String driverName;
    @Element(name = "DriverAddress", required = false)
    private String driverAddress;
    @Element(name = "DriverLicenseSeries", required = false)
    private String driverLicenseSeries;
    @Element(name = "DriverLicenseNumber", required = false)
    private String driverLicenseNumber;
    @Element(name = "DriverContact", required = false)
    private String driverContact;

    public String getDriverName() {
        return driverName;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public String getDriverLicenseSeries() {
        return driverLicenseSeries;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public String getDriverContact() {
        return driverContact;
    }
}
