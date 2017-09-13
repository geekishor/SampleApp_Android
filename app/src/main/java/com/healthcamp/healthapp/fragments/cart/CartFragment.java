package com.healthcamp.healthapp.fragments.cart;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.activity.HomeActivity;
import com.healthcamp.healthapp.adapter.CartFragmentAdapter;
import com.healthcamp.healthapp.adapter.CategoryFragmentAdapter;
import com.healthcamp.healthapp.adapter.DetailsFragmentPageAdapter;
import com.healthcamp.healthapp.helpers.DatabaseHelper;
import com.healthcamp.healthapp.models.Carts.CartModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements View.OnClickListener {
    DatabaseHelper databaseHelper;
    private FragmentManager fragmentManager;
    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cart_recycle_view);

        databaseHelper = new DatabaseHelper(recyclerView.getContext());
        ArrayList<CartModel> allLists = databaseHelper.getAllCartItems();
        CartFragmentAdapter adapter = new CartFragmentAdapter(recyclerView.getContext(), allLists);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fragmentManager = ((AppCompatActivity) recyclerView.getContext()).getSupportFragmentManager();
        Button b = (Button) view.findViewById(R.id.btn_checkOut);
        b.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_checkOut:
                Fragment fragment = new CheckoutFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container_wrapper, fragment, "CartFragment");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }


    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());

        String url = "http://www.flightradar24.com/AirportInfoService.php?airport=ORY&type=in";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response

                        try {

                            JSONObject o = new JSONObject(response);
                            JSONArray values = o.getJSONArray("flights");

                            for (int i = 0; i < values.length(); i++) {

                                JSONObject sonuc = values.getJSONObject(i);


                            }


                        } catch (JSONException ex) {
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        rq.add(stringRequest);
        // Inflate the layout for this fragment
        return view;
    }*/
}

