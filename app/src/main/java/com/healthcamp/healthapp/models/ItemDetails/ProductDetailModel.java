package com.healthcamp.healthapp.models.ItemDetails;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ITH-143 on 05-Sep-17.
 */

public class ProductDetailModel implements Parcelable {
    public String Id;
    public String Name;
    public String Description;
    public String TaxRate;
    public String ProductType;
    public String Price;
    public String ComparePrice;
    public String[] ImageUrls;

    public ProductDetailModel() {

    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTaxRate() {
        return TaxRate;
    }

    public void setTaxRate(String taxRate) {
        TaxRate = taxRate;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String[] getImageUrls() {
        return ImageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        ImageUrls = imageUrls;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getComparePrice() {
        return ComparePrice;
    }

    public void setComparePrice(String comparePrice) {
        ComparePrice = comparePrice;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
