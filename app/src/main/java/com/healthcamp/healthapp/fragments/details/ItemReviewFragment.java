package com.healthcamp.healthapp.fragments.details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.adapter.ItemReviewAdapter;
import com.healthcamp.healthapp.models.ItemDetails.ItemReviewsModel;

import java.util.ArrayList;

/**
 * Created by ITH-143 on 4/9/2017.
 */

public class ItemReviewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_item_details_review, container, false);
        ArrayList<ItemReviewsModel> allLists = populateDummyData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.item_details_review_recycler_view);
        ItemReviewAdapter adapter = new ItemReviewAdapter(allLists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    public ArrayList<ItemReviewsModel> populateDummyData() {
        ArrayList<ItemReviewsModel> itemReviewsModelsList = new ArrayList<ItemReviewsModel>();
        for (int i = 0; i <= 3; i++) {
            ItemReviewsModel itemReviewsModel = new ItemReviewsModel();
            itemReviewsModel.setUserName("John " + i);
            itemReviewsModel.setDescription("When introducing the composition to Lennon, McCartney assured him that he would \"fix\" the line \"the movement you need is on your shoulder\", reasoning that \"it's a stupid expression; it sounds like a parrot.\" Lennon replied: \"You won't, you know. That's the best line in the song.\"[13] McCartney retained the phrase;[4] he later said of his subsequent live performances of the song: \"that's the line when I think of John, and sometimes I get a little emotional during that moment.");
            itemReviewsModel.setDateTime("2017-10-05 07:15:12");
            itemReviewsModelsList.add(itemReviewsModel);
        }
        return itemReviewsModelsList;
    }
}
