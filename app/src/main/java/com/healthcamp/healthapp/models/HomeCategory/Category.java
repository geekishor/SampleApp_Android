package com.healthcamp.healthapp.models.HomeCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ITH-143 on 01-Sep-17.
 */

public class Category {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("sub-categories")
    @Expose
    private List< SubCateogory > subCategories = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List< SubCateogory > getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List< SubCateogory > subCategories) {
        this.subCategories = subCategories;
    }
}
