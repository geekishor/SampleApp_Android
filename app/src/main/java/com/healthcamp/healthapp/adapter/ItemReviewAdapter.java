package com.healthcamp.healthapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.models.ItemDetails.ItemReviewsModel;

import java.util.ArrayList;

/**
 * Created by Kishor Bikram Oli on 28-Aug-17.
 */


public class ItemReviewAdapter extends RecyclerView
        .Adapter<ItemReviewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "CategoryItemsAdapter";
    private ArrayList<ItemReviewsModel> mDataset;

    public class DataObjectHolder extends RecyclerView.ViewHolder
    {
        TextView itemReviewUserName;
        TextView itemReviewDescription;
        TextView itemReviewDateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            itemReviewUserName = (TextView) itemView.findViewById(R.id.itemReviewUserName);
            itemReviewDescription = (TextView) itemView.findViewById(R.id.itemReviewDescription);
            itemReviewDateTime = (TextView) itemView.findViewById(R.id.itemReviewDateTime);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int a = getAdapterPosition();
                    ItemReviewsModel newModel = mDataset.get(a);

                }
            });
        }

    }


    public ItemReviewAdapter(ArrayList<ItemReviewsModel> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review_card, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.itemReviewUserName.setText(mDataset.get(position).getUserName());
        holder.itemReviewDescription.setText(mDataset.get(position).getDescription());
        holder.itemReviewDateTime.setText(mDataset.get(position).getDateTime());
    }

    public void addItem(ItemReviewsModel dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}