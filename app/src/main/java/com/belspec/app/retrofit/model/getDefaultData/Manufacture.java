package com.belspec.app.retrofit.model.getDefaultData;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

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
