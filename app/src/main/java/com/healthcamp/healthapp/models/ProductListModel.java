package com.healthcamp.healthapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SONU on 25/09/15.
 */
public class ProductListModel implements Parcelable{

    private String id;
    private String name;
    private String product_type;
    private String price;
    private String compare_price;
    private String image;

    public ProductListModel(String id, String title, String product_type, String price, String compare_price, String image) {

        this.id = id;
        this.name = title;
        this.product_type = product_type;
        this.price = price;
        this.compare_price = compare_price;
        this.image = image;
    }

    public ProductListModel() {
    }

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

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompare_price() {
        return compare_price;
    }

    public void setCompare_price(String compare_price) {
        this.compare_price = compare_price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
