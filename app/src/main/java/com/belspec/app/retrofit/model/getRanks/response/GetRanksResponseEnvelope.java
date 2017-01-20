package com.belspec.app.retrofit.model.getRanks.response;

import com.belspec.app.retrofit.model.PositionItem;
import com.belspec.app.retrofit.model.RankItem;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
public class GetRanksResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetRanksResponse")
    private RankItem body;

    public RankItem getBody(){
        return body;
    }
}
