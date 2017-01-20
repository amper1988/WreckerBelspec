package com.belspec.app.retrofit.model;


import org.simpleframework.xml.Element;

public class Manufacture {
    @Element(name = "Name", required = false)
    private String name;

    @Element(name = "ModelList", required = false)
    private ModelItem modelItemList;

    public String getName() {
        return name;
    }

    public ModelItem getModelItemList(){
        return modelItemList;
    }

    public Model getModelItem(int pos){
        return modelItemList.getModel(pos);
    }
}
