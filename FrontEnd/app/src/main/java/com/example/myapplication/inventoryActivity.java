package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.deck);
        recyclerView.setLayoutManager(layoutManager);

        // Define a layout for RecyclerView
        mLayoutManager = new GridLayoutManager(mContext,3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] cards = {
                "A",
                "B",
                "C",
                "D",
                "E",
                "F",
                "G"
        };

        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new cardAdapter(mContext,cards);

        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        recyclerView.setAdapter(adapter);


    }
}
