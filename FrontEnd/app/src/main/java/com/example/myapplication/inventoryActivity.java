package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

<<<<<<< HEAD
import androidx.cardview.widget.CardView;
=======
>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

=======
>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
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


<<<<<<< HEAD
        layoutManager = new GridLayoutManager(this,1,LinearLayoutManager.HORIZONTAL, false);
=======
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
        recyclerView = (RecyclerView) findViewById(R.id.deck);
        recyclerView.setLayoutManager(layoutManager);

        // Define a layout for RecyclerView
        mLayoutManager = new GridLayoutManager(mContext,3);
        mRecyclerView.setLayoutManager(mLayoutManager);

<<<<<<< HEAD
        ArrayList<String> cards = new ArrayList<String>(
                Arrays.asList(
=======
        String[] cards = {
>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
                "A",
                "B",
                "C",
                "D",
                "E",
                "F",
<<<<<<< HEAD
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
=======
                "G"
        };

        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new cardAdapter(mContext,cards);

>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        recyclerView.setAdapter(adapter);


    }
}
