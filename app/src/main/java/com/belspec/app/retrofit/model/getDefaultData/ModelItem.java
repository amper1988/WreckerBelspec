package com.belspec.app.retrofit.model.getDefaultData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

public class ModelItem {
    @ElementList(entry = "ModelItem", inline = true)
    private List<Model> modelList;

    public List<Model> getModelList() {
        return modelList;
    }

    public Model getModel(int pos){
        return modelList.get(pos);
    }
}
