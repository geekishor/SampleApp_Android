package com.healthcamp.healthapp.models.HomeSales;

/**
 * Created by ITH-143 on 30-Aug-17.
 */

public class SalesProducts {

    public int Id;
    public String Image;
    public String Name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String slug) {
        Name = slug;
    }
}
