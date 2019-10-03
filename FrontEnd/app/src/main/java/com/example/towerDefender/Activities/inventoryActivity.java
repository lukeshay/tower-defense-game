package com.example.towerDefender.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.towerDefender.Card.cardAdapter;
import com.example.towerDefender.VolleyServices.VolleyResponseListener;
import com.example.towerDefender.VolleyServices.VolleyUtilities;
import com.example.towerDefender.Card.deckAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class inventoryActivity extends Activity {

    ConstraintLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(com.example.towerDefender.R.layout.inventory);

        mContext = getApplicationContext();

        mRelativeLayout = (ConstraintLayout) findViewById(com.example.towerDefender.R.id.relativeLayout);
        mRecyclerView = (RecyclerView) findViewById(com.example.towerDefender.R.id.recycler_view);


        layoutManager = new GridLayoutManager(this,1,LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) findViewById(com.example.towerDefender.R.id.deck);
        recyclerView.setLayoutManager(layoutManager);

        // Define a layout for RecyclerView
        mLayoutManager = new GridLayoutManager(mContext,3);
        mRecyclerView.setLayoutManager(mLayoutManager);


        VolleyUtilities.getRequest(this.getApplicationContext(), "http://coms-309-ss-5.misc.iastate.edu:8080/cards", new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(Object response) {
                Log.e("response",response.toString());
            }
        });

        ArrayList<String> cards = new ArrayList<String>(
                Arrays.asList(
                "A",
                "B",
                "C",
                "D",
                "E",
                "F",
                "G",
                "H",
                "I",
                "J",
                "K",
                "L",
                "M",
                "N",
                "O",
                "P",
                "Q",
                "R",
                "S",
                "T",
                "U",
                "V",
                "W",
                "X",
                "Y",
                "Z")
        );

        ArrayList<String> deck = new ArrayList<String>(
                Arrays.asList("a","b","c")
        );
        // Initialize a new instance of RecyclerView Adapter instance
        deckAdapter da = new deckAdapter(mContext,deck);
        mAdapter = new cardAdapter(mContext,cards, da);
        adapter = new deckAdapter(mContext, deck);
        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        recyclerView.setAdapter(adapter);


    }
}
