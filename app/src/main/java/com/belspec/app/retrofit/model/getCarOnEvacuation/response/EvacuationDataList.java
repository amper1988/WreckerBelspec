package com.belspec.app.retrofit.model.getCarOnEvacuation.response;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "return")
public class EvacuationDataList {
    @ElementList(entry = "EvacuationDataItem", inline = true)
    private List<EvacuationData> evacuationDataList;

    public List<EvacuationData> getEvacuationDataList() {
        return evacuationDataList;
    }
}
