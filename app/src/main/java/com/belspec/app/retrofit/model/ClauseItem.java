package com.belspec.app.retrofit.model;

import com.belspec.app.retrofit.model.Clause;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ClauseList")
public class ClauseItem {
    @ElementList(entry = "ClauseItem", inline = true)
    private List<Clause> clauseList;

    public List<Clause> getClauseList() {
        return clauseList;
    }
}
