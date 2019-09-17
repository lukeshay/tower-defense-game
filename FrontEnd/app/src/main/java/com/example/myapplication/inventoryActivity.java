package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
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
        setContentView(R.layout.inventory);

        mContext = getApplicationContext();

        mRelativeLayout = (ConstraintLayout) findViewById(R.id.relativeLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        layoutManager = new GridLayoutManager(this,1,LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.deck);
        recyclerView.setLayoutManager(layoutManager);

        // Define a layout for RecyclerView
        mLayoutManager = new GridLayoutManager(mContext,3);
        mRecyclerView.setLayoutManager(mLayoutManager);

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
