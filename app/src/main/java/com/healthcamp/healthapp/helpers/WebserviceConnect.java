package com.healthcamp.healthapp.helpers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthcamp.healthapp.interfaces.ServerCallBackInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ITH-143 on 18-Jun-17.
 */

public class WebserviceConnect {
    Context context;

    public void callWebService(final ServerCallBackInterface callBack, final HashMap<String, String> params, String webserviceUrl, Context mContext) {
        context = mContext;
        StringRequest request = new StringRequest(Request.Method.POST, webserviceUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Logger.e("CallWebService response: ", response);

                try {
                    JSONObject responseJson = new JSONObject(response);
                    if (responseJson.getString("status").equalsIgnoreCase("OK")) {
                        //Toast.makeText(context.getApplicationContext(), responseJson.getString("message") + "", Toast.LENGTH_SHORT).show();
                        callBack.onSuccess(responseJson);
                    } else {
                        Toast.makeText(context.getApplicationContext(), responseJson.getString("message") + "", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Logger.e("CallWebService error: ", e.getMessage() + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.e("CallWebService error: ", error.getMessage() + "");
                if (error instanceof NetworkError) {
                    Toast.makeText(context.getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context.getApplicationContext(), "Server error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context.getApplicationContext(), "Auth error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context.getApplicationContext(), "Parse error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(context.getApplicationContext(), "Timeout error", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (params.size() > 0) {

                    //map.put("")
                }
                return params;
            }
        };


        final RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        // request.setTag("assignment_task");
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                //progressDialog.dismiss();
            }
        });
        queue.add(request);
        //progressDialog.show();
    }
}
