package com.healthcamp.healthapp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.healthcamp.healthapp.MainActivity;
import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.adapter.HomeAdapter;
import com.healthcamp.healthapp.fragments.HomeFragment;
import com.healthcamp.healthapp.fragments.cart.CartFragment;
import com.healthcamp.healthapp.helpers.Api;
import com.healthcamp.healthapp.helpers.Service;
import com.healthcamp.healthapp.helpers.WebserviceConnect;
import com.healthcamp.healthapp.interfaces.ServerCallBackInterface;
import com.healthcamp.healthapp.models.Categories;
import com.healthcamp.healthapp.models.DataModel;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.MyData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.GONE;

public class HomeActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private static RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    private static GridLayoutManager manager;
    private MainResult categoryItems;
    private Fragment fragment = null;

    private FragmentManager fragmentManager;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 4) < 2 ? 2 : 1;
            }
        });
        hasPermissions(HomeActivity.this, PERMISSIONS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.inflateHeaderView(R.layout.nav_header_activity_main);
        TextView profileName = (TextView) header.findViewById(R.id.profile_name);
        profileName.setText("Hello");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_store) {
                    ViewPager viewPager1 = (ViewPager) findViewById(R.id.view_pager);
                    viewPager1.setCurrentItem(0);
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    assert drawer != null;
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_category) {
                    ViewPager viewPager1 = (ViewPager) findViewById(R.id.view_pager);
                    viewPager1.setCurrentItem(1);
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    assert drawer != null;
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_cart) {
                } else if (id == R.id.nav_wishlist) {

                } else if (id == R.id.nav_settings) {

                } else if (id == R.id.nav_login) {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    return true;
                }
                //mRecyclerView.setVisibility(GONE);
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                fragment = new CartFragment();
                transaction.replace(R.id.home_container_wrapper, fragment, "MainActivity");
                //transaction.addToBackStack("HomeActivity");

                transaction.commit();
                fragmentManager.executePendingTransactions();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                assert drawer != null;
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        callApi();
    }

    private void callApi(){
        try{
            pDialog = new ProgressDialog(this);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setMessage("Loading...");
            pDialog.show();
            WebserviceConnect webserviceConnect = new WebserviceConnect();
            webserviceConnect.callWebService(new ServerCallBackInterface() {

                public void onSuccess(JSONObject response) {

                    try {
                        Gson gson = new Gson();
                        categoryItems = gson.fromJson(response.toString(), MainResult.class);
                        adapter = new HomeAdapter(getApplicationContext(), categoryItems);
                        mRecyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
                        mRecyclerView.setLayoutManager(manager);
                        mRecyclerView.setAdapter(adapter);
                        pDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }, new HashMap<String, String>(), Api.homeUrl, this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, PERMISSIONS, 100);
                } else {
//                    Toast.makeText(context,"Some functon may not work properly",Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context,"Process stopped temporarily",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == 1) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            } else {

                System.exit(0);
            }
        }
    }
}


