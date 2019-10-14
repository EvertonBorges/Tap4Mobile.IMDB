package com.tap4mobile.imdb.model.IMDB;

public class production_companie {

    private String name;
    private Integer id;
    private String logo_path;
    private String origin_countries;

    public production_companie() {
    }

    public production_companie(String name, Integer id, String logo_path, String origin_countries) {
        this.name = name;
        this.id = id;
        this.logo_path = logo_path;
        this.origin_countries = origin_countries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getOrigin_countries() {
        return origin_countries;
    }

    public void setOrigin_countries(String origin_countries) {
        this.origin_countries = origin_countries;
    }

}