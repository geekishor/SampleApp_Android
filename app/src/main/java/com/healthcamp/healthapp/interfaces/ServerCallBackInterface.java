package com.healthcamp.healthapp.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ITH-143 on 18-Jun-17.
 */

public interface ServerCallBackInterface {
    void onSuccess(JSONObject response) throws JSONException;
}