package com.healthcamp.healthapp.adapter;

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

import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.fragments.SubCategoryFragment;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.models.Carts.CartModel;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.HomeCategory.SubCateogory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ITH-143 on 03-Sep-17.
 */

public class CartFragmentAdapter extends RecyclerView.Adapter<CartFragmentAdapter.ViewHolder> {
    Context context = null;
    private ArrayList<CartModel> allLists;

    public CartFragmentAdapter(Context context, ArrayList<CartModel> allLists) {
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
        String imageUrl = allLists.get(position).getImageUrl();
        String price = allLists.get(position).getPrice();
        String dateCreated = allLists.get(position).getDateCreated();
        String itemCount = allLists.get(position).getItemCount();
        holder.itemTitle.setText(name);
        holder.itemPrice.setText('$' + price);
        holder.dateTime.setText(dateCreated);
        holder.itemCount.setText(itemCount);
        Picasso.with(this.context).load(imageUrl).into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return allLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemPrice;
        public TextView dateTime,itemCount;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_cart_cv, parent, false));
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            dateTime = (TextView) itemView.findViewById(R.id.dateTime);
            itemCount = (TextView) itemView.findViewById(R.id.itemCount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = getAdapterPosition();
                    /*ArrayList< SubCateogory > subCatList = (ArrayList<SubCateogory>) allLists.getResult().getCategory().get(position).getSubCategories();

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(ApplicationVariables.SUB_CAT_LISTS, subCatList);

                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment fragment = new SubCategoryFragment();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.main_container_wrapper, fragment);
                    ft.commit();*/
                }
            });
        }
    }
}