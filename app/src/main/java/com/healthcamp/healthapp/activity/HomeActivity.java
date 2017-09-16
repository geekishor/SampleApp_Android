package com.healthcamp.healthapp.activity;

import android.Manifest;
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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.fragments.ListDataFragment;
import com.healthcamp.healthapp.fragments.cart.CartFragment;
import com.healthcamp.healthapp.utils.FragmentHelper;


public class HomeActivity extends AppCompatActivity {
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS
    };
    private Toolbar toolbar;
    private DrawerLayout drawer;

    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        hasPermissions(HomeActivity.this, PERMISSIONS);
        initView();
        /**
         * This will call the listFragment for first time
         * other will be handel from drawerEventTask
         */
        FragmentHelper.openFragment(ListDataFragment.getInstance(), ListDataFragment.TAG, this);
       // openFragment(ListDataFragment.getInstance(), ListDataFragment.TAG);
        drawerEventTask();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle); // this is depricated you will find alternative of this method in internet
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.inflateHeaderView(R.layout.nav_header_activity_main);
        TextView profileName = (TextView) header.findViewById(R.id.profile_name);
        profileName.setText("Hello");
    }

    private void drawerEventTask() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_store) {
                    assert drawer != null;
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_category) {
                    assert drawer != null;
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_cart) {
                    fragment = new CartFragment();
                } else if (id == R.id.nav_wishlist) {
                    //Add your Fragment as per requirement i just used for testing
                    fragment = new CartFragment();
                } else if (id == R.id.nav_settings) {
                    fragment = new CartFragment();
                } else if (id == R.id.nav_login) {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    return true;
                }

                //null is just TAG value if you want you can pass fragment.getTag()
                openFragment(fragment, null);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    /**
     * You can make this method in your utill class to for general purpose use
     * so that you don't need to repeat the code again and again Todo Clean code is more imp
     * @TODO if you want to see previous fragment when you go
     * @TODO if you have lots of fragment then you need pass TAG Not null value
     * @TODO transaction.addToBackStack(null);
     */
    private void openFragment(final Fragment fragment, String TAG) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(TAG);
        transaction.commit();

    }

    public void hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, PERMISSIONS, 100);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            } else {
                System.exit(0);
            }
        }
    }
}


