package com.healthcamp.healthapp.fragments.details;


import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.models.ItemDetails.ProductDetailModel;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by ITH-143 on 4/9/2017.
 */

public class ItemDetailsFragment extends Fragment {
    TextView productName, productPrice, productDetails;
    ImageView imageView;
    ProductDetailModel productDetailModel;
    CarouselView customCarouselView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        String[] size = {"", "8.2", "9", "9.5", "11", "12",};
        DetailsFragment detailsFragment = new DetailsFragment();
        productDetailModel = detailsFragment.getProductDetailModel();
        Spinner spin = (Spinner) view.findViewById(R.id.size_spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(getContext(), item.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Selected",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, size);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productName = (TextView) view.findViewById(R.id.textView_itemName);
        productPrice = (TextView) view.findViewById(R.id.textView_itemPrice);
        productDetails = (TextView) view.findViewById(R.id.textView_itemDetails);

        productName.setText(productDetailModel.getName());
        productPrice.setText(productDetailModel.getPrice());
        productDetails.setText(productDetailModel.getPrice());

        customCarouselView = (CarouselView) view.findViewById(R.id.customCarouselView);
        customCarouselView.setPageCount(productDetailModel.getImageUrls().length);
        //customCarouselView.setSlideInterval(4000);
        customCarouselView.setViewListener(viewListener);
        //customCarouselView.setImageClickListener(imageClickListner);
       // tvYes.setOnClickListener(this);
       // tvNo.setOnClickListener(this);

    }

    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = li.inflate(R.layout.carousel_view_custom, null);

            ImageView carouselImageView = (ImageView) customView.findViewById(R.id.carouselImageView);
            Picasso.with(getContext()).load(productDetailModel.getImageUrls()[position]).into(carouselImageView);
            return customView;
        }
    };
}
