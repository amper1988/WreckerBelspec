package com.belspec.app.retrofit.model.getDefaultData;

import org.simpleframework.xml.Element;
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
