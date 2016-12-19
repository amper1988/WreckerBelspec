package com.belspec.app.retrofit.model.getDefaultData.response;

import com.belspec.app.retrofit.model.getDefaultData.Clause;
import com.belspec.app.retrofit.model.getDefaultData.ClauseItem;
import com.belspec.app.retrofit.model.getDefaultData.Color;
import com.belspec.app.retrofit.model.getDefaultData.ColorItem;
import com.belspec.app.retrofit.model.getDefaultData.Manufacture;
import com.belspec.app.retrofit.model.getDefaultData.ManufactureItem;
import com.belspec.app.retrofit.model.getDefaultData.Model;
import com.belspec.app.retrofit.model.getDefaultData.ModelItem;
import com.belspec.app.retrofit.model.getDefaultData.Organization;
import com.belspec.app.retrofit.model.getDefaultData.OrganizationItem;
import com.belspec.app.retrofit.model.getDefaultData.Parking;
import com.belspec.app.retrofit.model.getDefaultData.ParkingItem;
import com.belspec.app.retrofit.model.getDefaultData.PoliceDepartment;
import com.belspec.app.retrofit.model.getDefaultData.PoliceDepartmentItem;
import com.belspec.app.retrofit.model.getDefaultData.Policeman;
import com.belspec.app.retrofit.model.getDefaultData.PolicemanItem;
import com.belspec.app.retrofit.model.getDefaultData.Wrecker;
import com.belspec.app.retrofit.model.getDefaultData.WreckerItem;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "return")
public class DefaultData {
    @Element(name = "ManufactureList", required = false)
    private ManufactureItem manufactureList;

    @Element(name = "PoliceDepartmentList", required = false)
    private PoliceDepartmentItem policeDepartmentList;

    @Element(name = "ClauseList", required = false)
    private ClauseItem clauseList;

    @Element(name = "OrganizationList", required = false)
    private OrganizationItem organizationList;

    @Element(name = "ParkingList", required = false)
    private ParkingItem parkingList;

    @Element(name = "ColorList", required = false)
    private ColorItem colorItem;

    public List<Manufacture> getManufactureList() {
        return manufactureList.getManufactureList();
    }

    public List<PoliceDepartment> getPoliceDepartmentList() {
        return policeDepartmentList.getPoliceDepartment();
    }

    public List<Clause> getClauseList() {
        return clauseList.getClauseList();
    }

    public List<Organization> getOrganizationList() {
        return organizationList.getOrganizationList();
    }

    public List<Parking> getParkingList() {
        return parkingList.getParkingList();
    }

    public List<Color> getColorList(){
        return this.colorItem.getColorList();
    }
}
