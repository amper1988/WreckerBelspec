package com.belspec.app.retrofit.model.getDefaultData;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ColorList")
public class ColorItem {
    @ElementList(entry = "ColorItem", inline = true)
    private List<Color> colorList;

    public List<Color> getColorList(){
        return this.colorList;
    }
}
