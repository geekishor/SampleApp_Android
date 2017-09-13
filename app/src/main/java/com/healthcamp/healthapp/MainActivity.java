package com.healthcamp.healthapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.healthcamp.healthapp.activity.LoginActivity;
import com.healthcamp.healthapp.adapter.SalesFragmentAdapter;
import com.healthcamp.healthapp.fragments.CategoryFragment;
import com.healthcamp.healthapp.fragments.HomeFragment;
import com.healthcamp.healthapp.fragments.cart.CartFragment;
import com.healthcamp.healthapp.fragments.details.DetailsFragment;
import com.healthcamp.healthapp.helpers.Api;
import com.healthcamp.healthapp.helpers.DatabaseHelper;
import com.healthcamp.healthapp.helpers.Service;
import com.healthcamp.healthapp.helpers.WebserviceConnect;
import com.healthcamp.healthapp.interfaces.ServerCallBackInterface;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.HomeSales.HomeSalesModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    private MainResult categoryItems;
    ArrayList<HomeSalesModel> salesItems;

    private SearchView searchView;
    private MenuItem searchMenuItem;

    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS
    };
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);

        hasPermissions(MainActivity.this, PERMISSIONS);

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

                    //  fragment = new HomeFragment();
                } else if (id == R.id.nav_cart) {
                    fragment = new CartFragment();
                } else if (id == R.id.nav_wishlist) {

                } else if (id == R.id.nav_settings) {

                } else if (id == R.id.nav_login) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    return true;
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container_wrapper, fragment, "MainActivity");
                transaction.addToBackStack(null);
                transaction.commit();
                fragmentManager.executePendingTransactions();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                assert drawer != null;
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    public ArrayList<HomeSalesModel> getSalesItems() {
        return salesItems;
    }

    public MainResult getCategoryItems() {
        return categoryItems;
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        pDialog = new ProgressDialog(this);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setMessage("Loading...");
        pDialog.show();
        WebserviceConnect webserviceConnect = new WebserviceConnect();
        webserviceConnect.callWebService(new ServerCallBackInterface() {

            public void onSuccess(JSONObject response) {

                try {
                    salesItems = Service.getSalesItems(response);
                    Gson gson = new Gson();
                    categoryItems = gson.fromJson(response.toString(), MainResult.class);
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragment = new HomeFragment();

                  /*  Bundle bundle = new Bundle();
                    bundle.putSerializable("MainResult", allCategoryLists);
                    bundle.putSerializable("SalesResult", allSalesLists);
                    fragment.setArguments(bundle);*/


                    fragmentTransaction.replace(R.id.main_container_wrapper, fragment, "homeFragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();

                    pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new HashMap<String, String>(), Api.homeUrl, this);


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
}
