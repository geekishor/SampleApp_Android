package com.healthcamp.healthapp.models;

/**
 * Created by ITH-143 on 4/7/2017.
 */

public class SingleItemModel {
    private String name;
    private String url;
    private String description;
    private String Id;

    public SingleItemModel() {

    }

    public SingleItemModel( String id, String name, String url) {
        this.name = name;
        this.url = url;
        this.Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
