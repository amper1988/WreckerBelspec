package com.belspec.app.retrofit.model.getCarOnEvacuation.response;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;


public class EvacuationData implements Parcelable{
    @Element(name = "Manufacture", required = false)
    private String manufacture;

    @Element(name = "Model", required = false)
    private String model;

    @Element(name = "CarID", required = false)
    private String carId;

    @Element(name = "Photo1", required = false)
    private String photo1;

    public String getColor() {
        return color;
    }

    @Element(name = "Color", required = false)
    private String color;

    @Element(name = "Photo2", required = false)
    private String photo2;

    @Element(name = "Photo3", required = false)
    private String photo3;

    @Element(name = "Photo4", required = false)
    private String photo4;

    @Element(name = "Address", required = false)
    private String address;

    @Element(name = "Clause", required = false)
    private String clause;

    @Element(name = "PoliceDepartment", required = false)
    private String policeDepartment;

    @Element(name = "Policeman", required = false)
    private String policeman;

    @Element(name = "Organization", required = false)
    private String organization;

    @Element(name = "Wrecker", required = false)
    private String wrecker;

    @Element(name = "EvacuationType", required = false)
    private int evacuationType;

    @Element(name = "ID", required = false)
    private int id;

    @Element(name = "EvacuationDate", required = false)
    private Date evacuationDate;

    @Element(name = "Protocol", required = false)
    private String protocol;

    public String getManufacture() {
        return manufacture;
    }

    public String getModel() {
        return model;
    }

    public String getCarId() {
        return carId;
    }

    public String getPhoto1() {
        return photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public String getAddress() {
        return address;
    }

    public String getClause() {
        return clause;
    }

    public String getPoliceDepartment() {
        return policeDepartment;
    }

    public String getPoliceman() {
        return policeman;
    }

    public String getOrganization() {
        return organization;
    }

    public String getWrecker() {
        return wrecker;
    }

    public int getEvacuationType() {
        return evacuationType;
    }

    public int getId() {
        return id;
    }

    public Date getEvacuationDate() {
        return evacuationDate;
    }

    public String getProtocol() {
        return protocol;
    }

    public  EvacuationData(){
        super();
    }
    protected EvacuationData(Parcel in) {
        manufacture = in.readString();
        model = in.readString();
        carId = in.readString();
        photo1 = in.readString();
        color = in.readString();
        photo2 = in.readString();
        photo3 = in.readString();
        photo4 = in.readString();
        address = in.readString();
        clause = in.readString();
        policeDepartment = in.readString();
        policeman = in.readString();
        organization = in.readString();
        wrecker = in.readString();
        evacuationType = in.readInt();
        id = in.readInt();
        protocol = in.readString();
    }

    public static final Creator<EvacuationData> CREATOR = new Creator<EvacuationData>() {
        @Override
        public EvacuationData createFromParcel(Parcel in) {
            return new EvacuationData(in);
        }

        @Override
        public EvacuationData[] newArray(int size) {
            return new EvacuationData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(manufacture);
        parcel.writeString(model);
        parcel.writeString(carId);
        parcel.writeString(photo1);
        parcel.writeString(color);
        parcel.writeString(photo2);
        parcel.writeString(photo3);
        parcel.writeString(photo4);
        parcel.writeString(address);
        parcel.writeString(clause);
        parcel.writeString(policeDepartment);
        parcel.writeString(policeman);
        parcel.writeString(organization);
        parcel.writeString(wrecker);
        parcel.writeInt(evacuationType);
        parcel.writeInt(id);
        parcel.writeString(protocol);
    }
}
