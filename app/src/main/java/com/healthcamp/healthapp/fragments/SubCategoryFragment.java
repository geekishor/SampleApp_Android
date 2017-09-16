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
import com.healthcamp.healthapp.activity.HomeActivity;
import com.healthcamp.healthapp.adapter.CategoryFragmentAdapter;
import com.healthcamp.healthapp.adapter.SubCategoryFragmentAdapter;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.helpers.FragmentTitle;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.HomeCategory.SubCateogory;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFragment extends Fragment {


    public SubCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        Bundle bundle = getArguments();
        ArrayList<SubCateogory> subCateogoryItems = bundle.getParcelableArrayList(ApplicationVariables.SUB_CAT_LISTS);
        String title = FragmentTitle.SubCategoryTitle;
        ((HomeActivity) getActivity()).setTitle(title);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.sub_category_recycle_view);
        SubCategoryFragmentAdapter adapter = new SubCategoryFragmentAdapter(recyclerView.getContext(), subCateogoryItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

}
