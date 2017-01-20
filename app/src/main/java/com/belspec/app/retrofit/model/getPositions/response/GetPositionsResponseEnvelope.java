package com.belspec.app.retrofit.model.getPositions.response;

import com.belspec.app.retrofit.model.Position;
import com.belspec.app.retrofit.model.PositionItem;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class GetPositionsResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetPositionsResponse")
    private PositionItem body;

    public PositionItem getBody(){
        return body;
    }
}
