package com.healthcamp.healthapp.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ITH-143 on 3/25/2017.
 */

public class Api {
    final static public String baseUrl = "http://admin.health-camp.com/api/v1/";
    final static public String loginUrl = baseUrl + "login/check_user/";
    final static public String homeUrl = baseUrl + "get-store";
    final static public String productsUrl = baseUrl + "get-products";
    final static public String productDetailUrl = baseUrl + "get-product-detail";


    public static boolean isInNetwork(Context ctx) {
        if (ctx == null)
            return false;

        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
