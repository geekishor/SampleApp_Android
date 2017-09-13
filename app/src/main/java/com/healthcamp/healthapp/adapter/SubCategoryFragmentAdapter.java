package com.healthcamp.healthapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.fragments.HomeFragment;
import com.healthcamp.healthapp.fragments.ProductListFragment;
import com.healthcamp.healthapp.fragments.SubCategoryFragment;
import com.healthcamp.healthapp.fragments.details.DetailsFragment;
import com.healthcamp.healthapp.helpers.Api;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.helpers.Service;
import com.healthcamp.healthapp.helpers.WebserviceConnect;
import com.healthcamp.healthapp.interfaces.ServerCallBackInterface;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.HomeCategory.SubCateogory;
import com.healthcamp.healthapp.models.ProductListModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by ITH-143 on 4/9/2017.
 */

public class SubCategoryFragmentAdapter extends RecyclerView.Adapter<SubCategoryFragmentAdapter.ViewHolder> {
    Context context = null;
    private ArrayList<SubCateogory> allLists;

    public SubCategoryFragmentAdapter(Context context, ArrayList<SubCateogory> allLists) {
        this.context = context;
        this.allLists = allLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = allLists.get(position).getName();
        String imageUrl = allLists.get(position).getImage();
        String description = allLists.get(position).getSlug();
        holder.categoryName.setText(name);
        //holder.categoryDes.setText(description);
        Picasso.with(this.context).load(imageUrl).into(holder.categoryHeadingImg);

    }

    @Override
    public int getItemCount() {
        return allLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView categoryHeadingImg;
        public TextView categoryName;
       //
       // public TextView categoryDes;
        public Fragment fragment = null;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.category_card, parent, false));
            categoryHeadingImg = (ImageView) itemView.findViewById(R.id.categoryHeadingImg);
            categoryName = (TextView) itemView.findViewById(R.id.category_title);
           // categoryDes = (TextView) itemView.findViewById(R.id.category_des);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = getAdapterPosition();
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    final FragmentTransaction ft = fm.beginTransaction();

                    if (allLists.get(position).getSubCategories() != null) {
                       ArrayList<SubCateogory> subCateogoryItems = allLists.get(position).getSubCategories();
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(ApplicationVariables.SUB_CAT_LISTS, subCateogoryItems);
                        fragment = new SubCategoryFragment();
                        fragment.setArguments(bundle);
                        ft.replace(R.id.main_container_wrapper, fragment, "subCategoryFragmentAdapter");
                        ft.addToBackStack(null);
                        ft.commit();

                    } else {
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Fetching detals...");
                        HashMap<String, String> params = new HashMap<String, String>();

                        String id = allLists.get(position).getId();
                        params.put("category_id", id);
                        progressDialog.show();

                        WebserviceConnect webserviceConnect = new WebserviceConnect();
                        webserviceConnect.callWebService(new ServerCallBackInterface() {

                            public void onSuccess(JSONObject response) throws JSONException {
                                ArrayList<ProductListModel> productListItems = Service.getProductItems(response);
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList(ApplicationVariables.PRODUCT_LIST_ITEMS, productListItems);
                                fragment = new ProductListFragment();
                                fragment.setArguments(bundle);
                                ft.replace(R.id.main_container_wrapper, fragment,"subCategoryFragmentAdapter");
                                ft.addToBackStack(null);
                                ft.commit();
                                progressDialog.hide();
                            }
                        }, params, Api.productsUrl, context);

                    }
                    //   fragment = new ProductListFragment();
                  //  ft.replace(R.id.main_container_wrapper, fragment);
                    //ft.commit();
                }
            });
        }
    }
}