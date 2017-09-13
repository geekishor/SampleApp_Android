package com.healthcamp.healthapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.healthcamp.healthapp.fragments.details.ItemDetailsFragment;
import com.healthcamp.healthapp.fragments.details.ItemReviewFragment;
import com.healthcamp.healthapp.fragments.details.ItemSimilarFragment;

/**
 * Created by ITH-143 on 03-Sep-17.
 */

public class DetailsFragmentPageAdapter extends FragmentPagerAdapter {

    private static final String TAG = DetailsFragmentPageAdapter.class.getSimpleName();

    private static final int FRAGMENT_COUNT = 3;

    public DetailsFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ItemDetailsFragment();
            case 1:
                  return new ItemReviewFragment();
            case 2:
                 return new ItemSimilarFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Details";
            case 1:
                return "Similar";
            case 2:
                return "Review";
        }
        return null;
    }
}