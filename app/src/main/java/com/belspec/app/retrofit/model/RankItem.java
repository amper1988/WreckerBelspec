package com.belspec.app.retrofit.model;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class RankItem {
    @ElementList(entry = "RankItem", inline = true)
    private List<Rank> rankList;

    public List<Rank> getRankList(){
        return rankList;
    }
}
