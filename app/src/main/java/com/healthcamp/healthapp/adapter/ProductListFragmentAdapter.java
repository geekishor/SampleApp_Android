package com.healthcamp.healthapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.fragments.ProductListFragment;
import com.healthcamp.healthapp.fragments.SubCategoryFragment;
import com.healthcamp.healthapp.fragments.details.DetailsFragment;
import com.healthcamp.healthapp.helpers.Api;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.helpers.DatabaseHelper;
import com.healthcamp.healthapp.helpers.Service;
import com.healthcamp.healthapp.helpers.WebserviceConnect;
import com.healthcamp.healthapp.interfaces.ServerCallBackInterface;
import com.healthcamp.healthapp.models.Carts.CartModel;
import com.healthcamp.healthapp.models.HomeCategory.SubCateogory;
import com.healthcamp.healthapp.models.ItemDetails.ProductDetailModel;
import com.healthcamp.healthapp.models.ProductListModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by SONU on 25/09/15.
 */
public class ProductListFragmentAdapter extends
        RecyclerView.Adapter<ProductListFragmentAdapter.RecyclerViewHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<ProductListModel> arrayList;
    private Context context;

    public ProductListFragmentAdapter(Context context, ArrayList<ProductListModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        final ProductListModel model = arrayList.get(position);

        RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;

        mainHolder.productTitle.setText(model.getName());
        mainHolder.productPrice.setText("$ " + model.getPrice());
        Picasso.with(context).load(model.getImage()).into(mainHolder.productImage);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(holder.overflow, position);
            }
        });

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_row, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView productTitle;
        public ImageView productImage;
        public TextView productPrice;
        public ImageView overflow;
        public Fragment fragment = null;
        ProductDetailModel productDetailModel = new ProductDetailModel();

        public RecyclerViewHolder(View view) {
            super(view);
            this.productTitle = (TextView) view.findViewById(R.id.product_title);
            this.productImage = (ImageView) view.findViewById(R.id.product_image);
            this.productPrice = (TextView) view.findViewById(R.id.product_price);
            this.overflow = (ImageView) view.findViewById(R.id.overflow);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = getAdapterPosition();
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    final FragmentTransaction ft = fm.beginTransaction();

                    if (arrayList.get(position).getId() != null) {
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Fetching details...");
                        HashMap<String, String> params = new HashMap<String, String>();

                        String id = arrayList.get(position).getId();
                        params.put("product_id", id);
                        progressDialog.show();

                        WebserviceConnect webserviceConnect = new WebserviceConnect();
                        webserviceConnect.callWebService(new ServerCallBackInterface() {

                            public void onSuccess(JSONObject response) throws JSONException {
                                productDetailModel = Service.getProductDetail(response);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(ApplicationVariables.PRODUCT_DETAILS, productDetailModel);
                                fragment = new DetailsFragment();
                                fragment.setArguments(bundle);
                                ft.replace(R.id.main_container_wrapper, fragment, "productListFragment");
                                ft.addToBackStack(null);
                                ft.commit();
                                progressDialog.hide();
                            }
                        }, params, Api.productDetailUrl, context);

                    }
                    //   fragment = new ProductListFragment();
                    //  ft.replace(R.id.main_container_wrapper, fragment);
                    //ft.commit();
                }
            });
        }
    }

    private void showPopUp(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_product, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_add_cart:
                        Toast.makeText(context, "Added to cart " + position, Toast.LENGTH_SHORT).show();
                        final String productId = arrayList.get(position).getId();

                        Service.addToCart(productId, context);

                        //  databaseHelper.insertCart(productListModel)
                        return true;
                    case R.id.action_add_wishlist:
                        Toast.makeText(context, "Added to wishlist " + position, Toast.LENGTH_SHORT).show();
                        ProductListModel wishListModel = arrayList.get(position);
                        return true;
                    default:
                }
                return false;
            }
        });
        popupMenu.show();
    }

/*    class MenuClickListener implements  PopupMenu.OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_cart:
                    Toast.makeText(context, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_add_wishlist:
                    Toast.makeText(context, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }*/
}
