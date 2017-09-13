package com.healthcamp.healthapp.models;

import java.util.ArrayList;

/**
 * Created by ITH-143 on 4/7/2017.
 */


public class SectionItemDataModel {
    private String headerTitle;
    private String slug;
    private ArrayList<SingleItemModel> allItemsInSection;

    public SectionItemDataModel(){

    }
    public SectionItemDataModel(String headerTitle, ArrayList<SingleItemModel> allItemsInSection, String slug){
        this.headerTitle = headerTitle;
        this.slug = slug;
        this.allItemsInSection = allItemsInSection;
    }

    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getHeaderTitle() {
        return headerTitle;

    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }
}
