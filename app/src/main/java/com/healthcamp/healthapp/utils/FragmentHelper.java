package com.healthcamp.healthapp.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.activity.HomeActivity;

/**
 * Created by ITH-143 on 14-Sep-17.
 */

public class FragmentHelper  {
    public static void openFragment(final Fragment fragment, String TAG, Context context) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(TAG);
        transaction.commit();

    }
    public static void openFragment(final Fragment fragment, String TAG, Context context, Bundle bundles) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundles);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(TAG);
        transaction.commit();

    }
}
