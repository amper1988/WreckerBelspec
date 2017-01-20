package com.belspec.app.retrofit.model;

import com.belspec.app.retrofit.model.PoliceDepartment;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "PoliceDepartmentList")
public class PoliceDepartmentItem {
    @ElementList(entry = "PoliceDepartmentItem", inline = true)
    private List<PoliceDepartment> policeDepartmentList;

    public List<PoliceDepartment> getPoliceDepartment() {
        return policeDepartmentList;
    }
}
