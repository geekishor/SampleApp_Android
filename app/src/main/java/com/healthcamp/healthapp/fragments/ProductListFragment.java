package com.healthcamp.healthapp.fragments;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.adapter.ProductListFragmentAdapter;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.models.ProductListModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {

    public ProductListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        Bundle bundle = getArguments();
        ArrayList<ProductListModel> allLists = bundle.getParcelableArrayList(ApplicationVariables.PRODUCT_LIST_ITEMS);

        getActivity().setTitle("Sales");
        final RecyclerView productRecyclerView = (RecyclerView) view.findViewById(R.id.product_item_fragment_rv);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(productRecyclerView.getContext(), 2);
       // productRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        productRecyclerView.setLayoutManager(mLayoutManager);
        productRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        productRecyclerView.setHasFixedSize(true);
        ProductListFragmentAdapter mAdapter = new ProductListFragmentAdapter(productRecyclerView.getContext(), allLists);
        productRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

