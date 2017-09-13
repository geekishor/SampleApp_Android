package com.healthcamp.healthapp.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.adapter.HomeFragmentAdapter;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.HomeSales.HomeSalesModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_tab_favourite,
            R.drawable.ic_tab_call,
            R.drawable.ic_tab_contacts
    };
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       /* Bundle bundle = getArguments();
        ArrayList<HomeSalesModel> arraylist  = (ArrayList<HomeSalesModel>) bundle.getSerializable("SalesResult");
        MainResult mainResult  = (MainResult) bundle.getSerializable("MainResult");*/

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new HomeFragmentAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        setUpTabIcons();
        return view;
    }
    private void setUpTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


}
