package com.belspec.app.retrofit.model.getDefaultData.response;

import com.belspec.app.retrofit.model.Clause;
import com.belspec.app.retrofit.model.ClauseItem;
import com.belspec.app.retrofit.model.Color;
import com.belspec.app.retrofit.model.ColorItem;
import com.belspec.app.retrofit.model.Manufacture;
import com.belspec.app.retrofit.model.ManufactureItem;
import com.belspec.app.retrofit.model.Organization;
import com.belspec.app.retrofit.model.OrganizationItem;
import com.belspec.app.retrofit.model.Parking;
import com.belspec.app.retrofit.model.ParkingItem;
import com.belspec.app.retrofit.model.PoliceDepartment;
import com.belspec.app.retrofit.model.PoliceDepartmentItem;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

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
