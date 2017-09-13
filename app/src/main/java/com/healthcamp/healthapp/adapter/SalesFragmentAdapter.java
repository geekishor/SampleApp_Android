package com.healthcamp.healthapp.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.fragments.ProductListFragment;
import com.healthcamp.healthapp.fragments.SubCategoryFragment;
import com.healthcamp.healthapp.helpers.Api;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.helpers.Service;
import com.healthcamp.healthapp.helpers.WebserviceConnect;
import com.healthcamp.healthapp.interfaces.ServerCallBackInterface;
import com.healthcamp.healthapp.models.HomeCategory.SubCateogory;
import com.healthcamp.healthapp.models.ProductListModel;
import com.healthcamp.healthapp.models.SectionItemDataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ITH-143 on 02-Sep-17.
 */

public class SalesFragmentAdapter extends RecyclerView.Adapter<SalesFragmentAdapter.ItemRowHolder> {
    Fragment fragment = new Fragment();
    private ArrayList<SectionItemDataModel> dataList;
    private Context mContext;

    public SalesFragmentAdapter(Context context, ArrayList<SectionItemDataModel> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dynamic_list_item, null);
        ItemRowHolder itemRowHolder = new ItemRowHolder(view);
        return itemRowHolder;
    }

    @Override
    public void onBindViewHolder(final ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();
        final String slug = dataList.get(i).getSlug();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();
        itemRowHolder.itemTitle.setText(sectionName);
        final SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);

        //itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
        //itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);

        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(v.getContext(), "click event on more, " + slug, Toast.LENGTH_SHORT).show();

                Context context = v.getContext();
                Activity a = scanForActivity(context);
                final FragmentManager fm = ((AppCompatActivity) a).getSupportFragmentManager();
                final FragmentTransaction ft = fm.beginTransaction();


                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Fetching details...");
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("product_type", slug);
                progressDialog.show();

                WebserviceConnect webserviceConnect = new WebserviceConnect();
                webserviceConnect.callWebService(new ServerCallBackInterface() {

                    public void onSuccess(JSONObject response) throws JSONException {
                        ArrayList<ProductListModel> productListItems = Service.getProductTypes(response);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(ApplicationVariables.PRODUCT_LIST_ITEMS, productListItems);
                        fragment = new ProductListFragment();
                        fragment.setArguments(bundle);
                        ft.replace(R.id.main_container_wrapper, fragment, "ProductListFragment");
                        int count = fm.getBackStackEntryCount();
                        ft.addToBackStack(null);
                        ft.commit();
                        progressDialog.hide();
                    }
                }, params, Api.productsUrl, context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;
        protected Button btnMore;

        public ItemRowHolder(View view) {

            super(view);
            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.btnMore = (Button) view.findViewById(R.id.btn_more);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);

        }
    }
    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }
}
