package com.healthcamp.healthapp.fragments.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.adapter.DetailsFragmentPageAdapter;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.helpers.Service;
import com.healthcamp.healthapp.models.HomeSales.HomeSalesModel;
import com.healthcamp.healthapp.models.ItemDetails.ProductDetailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ITH-143 on 4/6/2017.
 */

public class DetailsFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new DetailsFragmentPageAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        Bundle bundle = getArguments();
        ApplicationVariables.PRODUCT_DETAIL_MODEL = bundle.getParcelable(ApplicationVariables.PRODUCT_DETAILS);
        return view;
    }
    public ProductDetailModel getProductDetailModel() {
        return ApplicationVariables.PRODUCT_DETAIL_MODEL;
    }

}

