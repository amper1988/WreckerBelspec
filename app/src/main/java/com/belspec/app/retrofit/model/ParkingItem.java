package com.belspec.app.retrofit.model;

import com.belspec.app.retrofit.model.Parking;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ParkingList")
public class ParkingItem {
    @ElementList(entry = "ParkingItem", inline = true)
    private List<Parking> parkingList;

    public List<Parking> getParkingList() {
        return parkingList;
    }
}
