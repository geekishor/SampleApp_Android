package com.healthcamp.healthapp.models.Carts;

/**
 * Created by ITH-143 on 07-Sep-17.
 */

public class WishListModel {
    public String Id;
    public String Name;
    public String Description;
    public String DateCreated;
    public String Price;
    public String ImageUrl;
    public WishListModel(String id, String name, String description, String dateCreated, String price, String imageUrl){
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.DateCreated = dateCreated;
        this.Price= price;
        this.ImageUrl = imageUrl;
    }
    public WishListModel(String name, String description, String dateCreated, String price, String imageUrl){
        this.Name = name;
        this.Description = description;
        this.DateCreated = dateCreated;
        this.Price= price;
        this.ImageUrl = imageUrl;
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
}
