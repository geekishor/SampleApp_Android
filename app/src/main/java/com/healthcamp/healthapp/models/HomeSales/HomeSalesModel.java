package com.healthcamp.healthapp.models.HomeSales;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ITH-143 on 30-Aug-17.
 */

public class HomeSalesModel implements Serializable {
    public String Title;
    public String Slug;
    public String ImageUrl;
    public ArrayList<SalesProducts> Products;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSlug() {
        return Slug;
    }

    public void setSlug(String slug) {
        Slug = slug;
    }

    public ArrayList<SalesProducts> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<SalesProducts> products) {
        Products = products;
    }
}
