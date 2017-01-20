package com.belspec.app.retrofit.model;

import com.belspec.app.retrofit.model.Model;

import org.simpleframework.xml.ElementList;

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
