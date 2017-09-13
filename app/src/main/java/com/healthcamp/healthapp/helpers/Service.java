package com.healthcamp.healthapp.helpers;


import android.content.Context;
import android.util.Log;

import com.healthcamp.healthapp.interfaces.ServerCallBackInterface;
import com.healthcamp.healthapp.models.Carts.CartModel;
import com.healthcamp.healthapp.models.Categories;
import com.healthcamp.healthapp.models.HomeSales.HomeSalesModel;
import com.healthcamp.healthapp.models.HomeSales.SalesProducts;
import com.healthcamp.healthapp.models.ItemDetails.ProductDetailModel;
import com.healthcamp.healthapp.models.ProductListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ITH-143 on 18-Jun-17.
 */

public class Service {


    public static ArrayList<HomeSalesModel> getSalesItems(JSONObject jsonObject) throws JSONException {
        JSONObject allLists = jsonObject.getJSONObject("results");
        JSONArray saleArray = allLists.getJSONArray("sale");
        ArrayList<HomeSalesModel> saleList = new ArrayList<HomeSalesModel>();
        for (int i = 0; i < saleArray.length(); i++) {
            JSONObject jObj = saleArray.getJSONObject(i);
            HomeSalesModel salesModel = new HomeSalesModel();
            salesModel.setTitle(jObj.getString("title"));
            salesModel.setSlug(jObj.getString("slug"));

            JSONArray productsArray = jObj.getJSONArray("products");
            ArrayList<SalesProducts> salesProducts = new ArrayList<SalesProducts>();
            for (int j = 0; j < productsArray.length(); j++) {
                JSONObject childObj = productsArray.getJSONObject(j);
                SalesProducts salesProductsModel = new SalesProducts();
                salesProductsModel.setId(childObj.getInt("id"));
                salesProductsModel.setName(childObj.getString("name"));
                salesProductsModel.setImage(childObj.getString("image"));
                salesProducts.add(salesProductsModel);
            }
            salesModel.setProducts(salesProducts);

            saleList.add(salesModel);
        }
        return saleList;
    }

    public static ArrayList<ProductListModel> getProductItems(JSONObject jsonObject) throws JSONException {
        JSONArray allLists = jsonObject.getJSONArray("results");
        ArrayList<ProductListModel> saleList = new ArrayList<ProductListModel>();
        for (int i = 0; i < allLists.length(); i++) {
            JSONObject jObj = allLists.getJSONObject(i);
            ProductListModel salesModel = new ProductListModel();
            salesModel.setName(jObj.get("name").toString());
            salesModel.setId(jObj.get("id").toString());
            salesModel.setPrice(jObj.get("price").toString());
            salesModel.setProduct_type(jObj.get("product_type").toString());
            salesModel.setCompare_price(jObj.get("compare_price").toString());
            salesModel.setImage(jObj.get("image").toString());

            saleList.add(salesModel);
        }
        return saleList;
    }

    public static ProductDetailModel getProductDetail(JSONObject jsonObject) throws JSONException {
        JSONObject jObj = jsonObject.getJSONObject("results");
        JSONArray imageURLs = jObj.getJSONArray("images");
        String[] imageUrls = new String[imageURLs.length()];
        ProductDetailModel productDetailModel = new ProductDetailModel();
        productDetailModel.setId(jObj.getString("id"));
        productDetailModel.setPrice(jObj.getString("price"));
        productDetailModel.setName(jObj.getString("name"));
        productDetailModel.setComparePrice(jObj.getString("compare_price"));
        productDetailModel.setDescription(jObj.getString("description"));
        productDetailModel.setTaxRate(jObj.getString("tax_rate"));
        productDetailModel.setProductType(jObj.getString("product_type"));
        for (int i = 0; i < imageURLs.length(); i++) {
            imageUrls[i] = imageURLs.get(i).toString();
        }
        productDetailModel.setImageUrls(imageUrls);
        return productDetailModel;
    }

    public static ArrayList<ProductListModel> getProductTypes(JSONObject jsonObject) throws JSONException {
        JSONArray allLists = jsonObject.getJSONArray("results");
        ArrayList<ProductListModel> saleList = new ArrayList<ProductListModel>();
        for (int i = 0; i < allLists.length(); i++) {
            JSONObject jObj = allLists.getJSONObject(i);
            ProductListModel salesModel = new ProductListModel();
            salesModel.setName(jObj.get("name").toString());
            salesModel.setId(jObj.get("id").toString());
            salesModel.setPrice(jObj.get("price").toString());
            salesModel.setCompare_price(jObj.get("compare_price").toString());
            salesModel.setImage(jObj.get("image").toString());

            saleList.add(salesModel);
        }
        return saleList;
    }

/*    public static ArrayList<Categories> getCategories(JSONObject jsonObject) throws JSONException {
        JSONArray allLists = jsonObject.getJSONArray("results");

        String name = salesItems.get(i).getProducts().get(j).getName();
        String imageUrl = salesItems.get(i).getProducts().get(j).getImage();
        ArrayList<Categories> saleList = new ArrayList<Categories>();
        for (int i = 0; i < allLists.length(); i++) {
            JSONObject jObj = allLists.getJSONObject(i);
            ProductListModel salesModel = new ProductListModel();
            salesModel.setName(jObj.get("name").toString());
            salesModel.setId(jObj.get("id").toString());
            salesModel.setPrice(jObj.get("price").toString());
            salesModel.setCompare_price(jObj.get("compare_price").toString());
            salesModel.setImage(jObj.get("image").toString());

            saleList.add(salesModel);
        }
        return saleList;
    }*/
    public static void addToCart(String productId, Context context) {
        try {
            final DatabaseHelper databaseHelper = new DatabaseHelper(context);

            HashMap<String, String> params = new HashMap<String, String>();

            params.put("product_id", productId);

            WebserviceConnect webserviceConnect = new WebserviceConnect();
            webserviceConnect.callWebService(new ServerCallBackInterface() {

                public void onSuccess(JSONObject response) throws JSONException {
                    ProductDetailModel productDetailModel = Service.getProductDetail(response);
                    String imageUrl = productDetailModel.getImageUrls()[0];

                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    CartModel cartModel = new CartModel(
                            productDetailModel.getId(),
                            productDetailModel.getName(),
                            productDetailModel.getDescription(),
                            productDetailModel.getTaxRate(),
                            productDetailModel.getProductType(),
                            productDetailModel.getPrice(),
                            productDetailModel.getComparePrice(),
                            imageUrl,
                            timeStamp,
                            "1"
                    );
                    databaseHelper.insertCart(cartModel);

                }
            }, params, Api.productDetailUrl, context);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }
}
