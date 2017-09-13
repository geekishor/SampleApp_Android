package com.healthcamp.healthapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthcamp.healthapp.MainActivity;
import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.adapter.CategoryFragmentAdapter;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.HomeSales.HomeSalesModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        MainActivity activity = (MainActivity) getActivity();
        MainResult categoryItems = activity.getCategoryItems();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.category_recycle_view);
        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(recyclerView.getContext(), categoryItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return  view;
    }

}
