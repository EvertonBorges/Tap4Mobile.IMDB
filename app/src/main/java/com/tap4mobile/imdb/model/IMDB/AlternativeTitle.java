package com.tap4mobile.imdb.model.IMDB;

import com.google.gson.Gson;

public class AlternativeTitle {

    private Integer id;
    private Title[] titles;

    public AlternativeTitle() {
    }

    public AlternativeTitle(Integer id, Title[] titles) {
        this.id = id;
        this.titles = titles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Title[] getTitles() {
        return titles;
    }

    public void setTitles(Title[] titles) {
        this.titles = titles;
    }

    public static AlternativeTitle JsonToObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AlternativeTitle.class);
    }

}