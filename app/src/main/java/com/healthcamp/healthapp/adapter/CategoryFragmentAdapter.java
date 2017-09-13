package com.healthcamp.healthapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.healthcamp.healthapp.fragments.details.DetailsFragment;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.helpers.FragmentTitle;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.HomeCategory.Results;
import com.healthcamp.healthapp.models.HomeCategory.SubCateogory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ITH-143 on 4/9/2017.
 */

public class CategoryFragmentAdapter extends RecyclerView.Adapter<CategoryFragmentAdapter.ViewHolder> {
    Context context = null;
    private MainResult allLists;

    public CategoryFragmentAdapter(Context context, MainResult allLists) {
        this.context = context;
        this.allLists = allLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = allLists.getResult().getCategory().get(position).getName();
        String imageUrl = allLists.getResult().getCategory().get(position).getImage();
        String description = allLists.getResult().getCategory().get(position).getSlug();
        holder.categoryName.setText(name);
       // holder.categoryDes.setText(description);
        Picasso.with(this.context).load(imageUrl).into(holder.categoryHeadingImg);

    }

    @Override
    public int getItemCount() {
        return allLists.getResult().getCategory().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView categoryHeadingImg;
        public TextView categoryName;


        //public TextView categoryDes;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.category_card, parent, false));
            categoryHeadingImg = (ImageView) itemView.findViewById(R.id.categoryHeadingImg);
            categoryName = (TextView) itemView.findViewById(R.id.category_title);
          //  categoryDes = (TextView) itemView.findViewById(R.id.category_des);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = getAdapterPosition();
                    ArrayList< SubCateogory > subCatList = (ArrayList<SubCateogory>) allLists.getResult().getCategory().get(position).getSubCategories();
                    FragmentTitle.SubCategoryTitle = allLists.getResult().getCategory().get(position).getName();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(ApplicationVariables.SUB_CAT_LISTS, subCatList);

                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);

                    Fragment fragment = new SubCategoryFragment();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.main_container_wrapper, fragment, "categoryFragmentAdapter");
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }
    }
}