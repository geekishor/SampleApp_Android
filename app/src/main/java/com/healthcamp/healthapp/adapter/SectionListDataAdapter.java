package com.healthcamp.healthapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.fragments.details.DetailsFragment;
import com.healthcamp.healthapp.helpers.Api;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.helpers.Service;
import com.healthcamp.healthapp.helpers.WebserviceConnect;
import com.healthcamp.healthapp.interfaces.ServerCallBackInterface;
import com.healthcamp.healthapp.models.ItemDetails.ProductDetailModel;
import com.healthcamp.healthapp.models.SingleItemModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ITH-143 on 4/7/2017.
 */

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private Context mContext;
    private ArrayList<SingleItemModel> itemsList;

    public SectionListDataAdapter(Context mContext, ArrayList<SingleItemModel> itemList) {
        this.mContext = mContext;
        this.itemsList = itemList;

    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dynamic_list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        SingleItemModel singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getName());
        Picasso.with(mContext).load(singleItem.getUrl()).into(holder.salesItemImage);

       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView tvTitle;
        protected ImageView salesItemImage;
        public Fragment fragment = null;
        public ImageButton cartButton, wishListButton, shareButton;

        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.salesItemImage = (ImageView) view.findViewById(R.id.salesItemImage);
            this.shareButton = (ImageButton) view.findViewById(R.id.share_button);
            this.cartButton = (ImageButton) view.findViewById(R.id.cart_button);
            this.wishListButton = (ImageButton) view.findViewById(R.id.favorite_button);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                    //v.getContext().startActivity(intent);
                    FragmentManager fm = ((AppCompatActivity) mContext).getSupportFragmentManager();
                    final FragmentTransaction ft = fm.beginTransaction();
                    final ProgressDialog progressDialog = new ProgressDialog(mContext);
                    progressDialog.setMessage("Fetching details...");
                    HashMap<String, String> params = new HashMap<String, String>();
                    int position = getAdapterPosition();
                    String id = itemsList.get(position).getId();
                    params.put("product_id", id);
                    progressDialog.show();

                    WebserviceConnect webserviceConnect = new WebserviceConnect();
                    webserviceConnect.callWebService(new ServerCallBackInterface() {

                        public void onSuccess(JSONObject response) throws JSONException {
                            ProductDetailModel productDetailModel = Service.getProductDetail(response);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(ApplicationVariables.PRODUCT_DETAILS, productDetailModel);
                            fragment = new DetailsFragment();
                            fragment.setArguments(bundle);
                            ft.replace(R.id.main_container_wrapper, fragment, "SectionListDataAdapter");
                            ft.addToBackStack(null);
                            ft.commit();
                            progressDialog.hide();
                        }
                    }, params, Api.productDetailUrl, mContext);
                    Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                }
            });


            this.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String name = itemsList.get(position).getName();
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/html");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, name);
                    mContext.startActivity(Intent.createChooser(sharingIntent,"Share using"));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }
}