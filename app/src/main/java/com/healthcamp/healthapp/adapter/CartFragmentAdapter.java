package com.healthcamp.healthapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.fragments.SubCategoryFragment;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.models.Carts.CartModel;
import com.healthcamp.healthapp.models.DataModel;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.HomeCategory.SubCateogory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.Handler;

/**
 * Created by ITH-143 on 03-Sep-17.
 */

public class CartFragmentAdapter extends RecyclerView.Adapter<CartFragmentAdapter.ItemViewHolder> {
    Context context = null;
    private ArrayList<CartModel> allLists;
    private ArrayList<CartModel> itemsPendingRemoval;

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private Handler handler = new Handler();
    HashMap<CartModel, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be

    public CartFragmentAdapter(Context context, ArrayList<CartModel> allLists) {
        this.context = context;
        this.allLists = allLists;
        itemsPendingRemoval = new ArrayList<CartModel>();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        final CartModel data = allLists.get(position);
        if (itemsPendingRemoval.contains(data)) {
            /** {show swipe layout} and {hide regular layout} */
            holder.regularLayout.setVisibility(View.GONE);
            holder.swipeLayout.setVisibility(View.VISIBLE);
            holder.undo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    undoOpt(data);
                }
            });
        } else {
            /** {show regular layout} and {hide swipe layout} */
            holder.regularLayout.setVisibility(View.VISIBLE);
            holder.swipeLayout.setVisibility(View.GONE);
           // holder.listItem.setText(data.getName());
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




    }

    private void undoOpt(CartModel customer) {
        Runnable pendingRemovalRunnable = pendingRunnables.get(customer);
        pendingRunnables.remove(customer);
        if (pendingRemovalRunnable != null)
            handler.removeCallbacks(pendingRemovalRunnable);
            itemsPendingRemoval.remove(customer);
        // this will rebind the row in "normal" state
        notifyItemChanged(allLists.indexOf(customer));
    }

    public void pendingRemoval(int position) {

        final CartModel data = allLists.get(position);
        if (!itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.add(data);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the data
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(allLists.indexOf(data));
                }
            };
             handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(data, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        CartModel data = allLists.get(position);
        if (itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.remove(data);
        }
        if (allLists.contains(data)) {
            allLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isPendingRemoval(int position) {
        CartModel data = allLists.get(position);
        return itemsPendingRemoval.contains(data);
    }

    @Override
    public int getItemCount() {
        return allLists.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
      public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemPrice;
        public TextView dateTime, itemCount;

        public CardView regularLayout;
        public CardView swipeLayout;
       // public TextView listItem;
        public TextView undo;

        public ItemViewHolder(View itemView) {
            super(itemView);
            regularLayout = (CardView) itemView.findViewById(R.id.card_view_category);
           // listItem = (TextView) itemView.findViewById(R.id.list_item);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            dateTime = (TextView) itemView.findViewById(R.id.dateTime);
            itemCount = (TextView) itemView.findViewById(R.id.itemCount);
            swipeLayout = (CardView) itemView.findViewById(R.id.swipeLayout);
            undo = (TextView) itemView.findViewById(R.id.undo);
        }
    }
}