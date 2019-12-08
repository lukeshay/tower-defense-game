package com.example.towerDefender.Activities;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.DeckAdapter;
import com.example.towerDefender.Card.OwnedDeck;
import com.example.towerDefender.Card.CardAdapter;
import com.example.towerDefender.R;
import com.example.towerDefender.Util.JsonUtility;
import com.example.towerDefender.VolleyServices.VolleyResponseListener;
import com.example.towerDefender.VolleyServices.VolleyUtilities;

import org.json.JSONObject;

import java.util.ArrayList;

public class InventoryActivity extends Activity {

    ConstraintLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DeckAdapter da;

    private ArrayList<OwnedDeck> decks;
    private ArrayList<Card> inventory;

    private ToggleButton deck1;
    private ToggleButton deck2;
    private ToggleButton deck3;

    private int selected = 0;

    private Button save;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(com.example.towerDefender.R.layout.inventory);

        mContext = getApplicationContext();
        save = (Button) findViewById(R.id.save);
        mRelativeLayout = (ConstraintLayout) findViewById(com.example.towerDefender.R.id.relativeLayout);
        mRecyclerView = (RecyclerView) findViewById(com.example.towerDefender.R.id.recycler_view);

        deck1 = findViewById(R.id.deck1);
        deck2 = findViewById(R.id.deck2);
        deck3 = findViewById(R.id.deck3);

        deck1.setOnCheckedChangeListener(changeListener);
        deck2.setOnCheckedChangeListener(changeListener);
        deck3.setOnCheckedChangeListener(changeListener);

        deck1.setText("Deck 1");
        deck2.setText("Deck 2");
        deck3.setText("Deck 3");



        layoutManager = new GridLayoutManager(this,1,LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) findViewById(com.example.towerDefender.R.id.deck);
        recyclerView.setLayoutManager(layoutManager);

        // Define a layout for RecyclerView
        mLayoutManager = new GridLayoutManager(mContext,3);
        mRecyclerView.setLayoutManager(mLayoutManager);


        VolleyUtilities.getRequest(this.getApplicationContext(), "http://coms-309-ss-5.misc.iastate.edu:8080/users/" +  "test1" /* Settings.Secure.getString(getApplicationContext().getContentResolver(), ANDROID_ID) */ + "/ownedCards", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("error",message);
            }

            @Override
            public void onResponse(Object response) {
                Log.e("response",response.toString());
                inventory = new ArrayList<>(JsonUtility.jsonToCardArray(response.toString()));
                // Initialize a new instance of RecyclerView Adapter instance
                da = new DeckAdapter(mContext,null);
                mAdapter = new CardAdapter(mContext,inventory, da);
                //adapter = new DeckAdapter(mContext, inventory);
                // Set the adapter for RecyclerView
                mRecyclerView.setAdapter(mAdapter);
                recyclerView.setAdapter(da);
                VolleyUtilities.getRequest(getApplicationContext(), "http://coms-309-ss-5.misc.iastate.edu:8080/users/test1/deck", new VolleyResponseListener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        Log.e("deck", response.toString());
                        decks = new ArrayList<>(JsonUtility.jsonToOwnedDecksArray(response.toString()));
                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast;
                toast = Toast.makeText(getApplicationContext(), "Deck has been saved", Toast.LENGTH_SHORT);
                toast.show();
                VolleyUtilities.getRequest(getApplicationContext(), "http://coms-309-ss-5.misc.iastate.edu:8080/users/test1/deck", new VolleyResponseListener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        Log.e("deck", response.toString());
                        decks = new ArrayList<>(JsonUtility.jsonToOwnedDecksArray(response.toString()));
                        JSONObject emptyjsonObject = new JSONObject();
                        if(da.getDeck().size() == 0){
                            for (Card x : decks.get(selected).get_deck()){
                                VolleyUtilities.deleteRequest(getApplicationContext(), "http://coms-309-ss-5.misc.iastate.edu:8080/users/deck/deleteCard/" + decks.get(selected).get_deckId() + "/" + x.cardName.replace(" ", "%20"), new VolleyResponseListener() {
                                    @Override
                                    public void onError(String message) {

                                    }

                                    @Override
                                    public void onResponse(Object response) {
                                        Log.e("removed", "removed card");
                                    }
                                });
                            }
                        }


                        else {
                            for (Card x : da.getDeck()) {
                                VolleyUtilities.postRequest(getApplicationContext(), "http://coms-309-ss-5.misc.iastate.edu:8080/users/deck/" + decks.get(selected).get_deckId() + "/" + x.cardName.replace(" ", "%20"), new VolleyResponseListener() {
                                    @Override
                                    public void onError(String message) {
                                        Log.e("error on card add", message);
                                    }

                                    @Override
                                    public void onResponse(Object response) {
                                        Log.e("added", "added to deck 1");
                                    }
                                }, emptyjsonObject);
                            }
                        }

                for(Card x:da.getDeck()){
                    Log.e("saved", x.cardName);
                }
            }
        });
            }
        });
    }
    CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
            VolleyUtilities.getRequest(getApplicationContext(), "http://coms-309-ss-5.misc.iastate.edu:8080/users/test1/deck", new VolleyResponseListener() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(Object response) {
                    Log.e("deck", response.toString());
                    decks = new ArrayList<>(JsonUtility.jsonToOwnedDecksArray(response.toString()));

                if (b) {
                    if (compoundButton == deck1) {
                        selected = 0;
                        deck2.setChecked(false);
                        deck3.setChecked(false);
                        da = new DeckAdapter(mContext, decks.get(0).get_deck());
                    }
                    if (compoundButton == deck2) {
                        selected = 1;
                        deck1.setChecked(false);
                        deck3.setChecked(false);
                        da = new DeckAdapter(mContext, decks.get(1).get_deck());
                    }
                    if (compoundButton == deck3) {
                        selected = 2;
                        deck1.setChecked(false);
                        deck2.setChecked(false);
                        da = new DeckAdapter(mContext, decks.get(2).get_deck());
                    }
                    mAdapter = new CardAdapter(mContext, inventory, da);
                    //adapter = new DeckAdapter(mContext, inventory);
                    // Set the adapter for RecyclerView
                    mRecyclerView.setAdapter(mAdapter);
                    recyclerView.setAdapter(da);
                }
                }
            });
        }
    };
}
