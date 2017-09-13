package com.healthcamp.healthapp.models.Carts;

/**
 * Created by ITH-143 on 07-Sep-17.
 */

public class CartModel {
    public String Id;
    public String Name;
    public String Description;
    public String TaxRate;
    public String ProductType;
    public String Price;
    public String ComparePrice;
    public String ImageUrl;
    public String DateCreated;
    public String ItemCount;

    public CartModel(String id, String name, String description, String taxRate, String productType, String price, String comparePrice, String imageUrl, String dateCreated, String itemCount) {
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.TaxRate = taxRate;
        this.ProductType = productType;
        this.Price = price;
        this.ComparePrice = comparePrice;
        this.ImageUrl = imageUrl;
        this.DateCreated = dateCreated;
        this.ItemCount = itemCount;
    }


    public CartModel(String name, String description, String taxRate, String productType, String price, String comparePrice, String imageUrl, String dateCreated, String itemCount) {
        this.Name = name;
        this.Description = description;
        this.TaxRate = taxRate;
        this.ProductType = productType;
        this.Price = price;
        this.ComparePrice = comparePrice;
        this.ImageUrl = imageUrl;

        this.DateCreated = dateCreated;
        this.ItemCount = itemCount;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
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

    public String getComparePrice() {
        return ComparePrice;
    }

    public void setComparePrice(String comparePrice) {
        ComparePrice = comparePrice;
    }

    public String getItemCount() {
        return ItemCount;
    }

    public void setItemCount(String itemCount) {
        ItemCount = itemCount;
    }

}
