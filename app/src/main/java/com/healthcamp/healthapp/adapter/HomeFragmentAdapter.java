package com.healthcamp.healthapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.healthcamp.healthapp.fragments.CategoryFragment;
import com.healthcamp.healthapp.fragments.SalesFragment;

/**
 * Created by ITH-143 on 02-Sep-17.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private static final String TAG = HomeFragmentAdapter.class.getSimpleName();

    private static final int FRAGMENT_COUNT = 2;
    public HomeFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SalesFragment();

            case 1:
                return new CategoryFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Sales";
            case 1:
                return "Category";
            case 2:
        }
        return null;
    }
}
