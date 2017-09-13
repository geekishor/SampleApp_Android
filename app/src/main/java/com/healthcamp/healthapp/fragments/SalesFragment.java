package com.healthcamp.healthapp.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.healthcamp.healthapp.MainActivity;
import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.adapter.SalesFragmentAdapter;
import com.healthcamp.healthapp.helpers.Api;
import com.healthcamp.healthapp.helpers.Service;
import com.healthcamp.healthapp.helpers.WebserviceConnect;
import com.healthcamp.healthapp.interfaces.ServerCallBackInterface;
import com.healthcamp.healthapp.models.HomeCategory.Category;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.HomeSales.HomeSalesModel;
import com.healthcamp.healthapp.models.SectionItemDataModel;
import com.healthcamp.healthapp.models.SingleItemModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment {

    ArrayList<SectionItemDataModel> allSampleData = new ArrayList<SectionItemDataModel>();

    public SalesFragment() {
        // Required empty public constructor
    }

    //add the onCreate() override
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        MainActivity activity = (MainActivity) getActivity();
        ArrayList<HomeSalesModel> salesItems = activity.getSalesItems();

        populateData(salesItems);  //add this here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales, container, false);


        getActivity().setTitle("Sales");
        final RecyclerView salesRecyclerView = (RecyclerView) view.findViewById(R.id.sales_fragment_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        salesRecyclerView.setLayoutManager(linearLayoutManager);
        salesRecyclerView.setHasFixedSize(true);

        SalesFragmentAdapter mAdapter = new SalesFragmentAdapter(salesRecyclerView.getContext(), allSampleData);
        salesRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public void populateData(ArrayList<HomeSalesModel> salesItems) {
        try {

            for (int i = 0; i < salesItems.size(); i++) {
                SectionItemDataModel dataModel = new SectionItemDataModel();
                dataModel.setHeaderTitle(salesItems.get(i).getTitle());
                dataModel.setSlug(salesItems.get(i).getSlug());
                ArrayList<SingleItemModel> singleItemModels = new ArrayList<SingleItemModel>();
                int productCount = salesItems.get(i).getProducts().size();
                for (int j = 0; j < productCount; j++) {
                    String id = String.valueOf(salesItems.get(i).getProducts().get(j).getId());
                    String name = salesItems.get(i).getProducts().get(j).getName();
                    String imageUrl = salesItems.get(i).getProducts().get(j).getImage();
                    singleItemModels.add(new SingleItemModel(id, name, imageUrl));
                }
                dataModel.setAllItemsInSection(singleItemModels);
                allSampleData.add(dataModel);
            }
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }

}
