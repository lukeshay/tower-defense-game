package com.example.towerDefender.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.towerDefender.Card.OwnedDeck;
import com.example.towerDefender.R;
import com.example.towerDefender.Util.CardUtilities;
import com.example.towerDefender.Util.JsonUtility;
import com.example.towerDefender.Util.UserUtility;
import com.example.towerDefender.VolleyServices.VolleyResponseListener;
import com.example.towerDefender.VolleyServices.VolleyUtilities;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity {
    Toast featureUnderProductionToast;
    private PopupWindow mPopupWindow;
    private Button deck1;
    private Button deck2;
    private Button deck3;
    private ArrayList<OwnedDeck> decks;
    public static OwnedDeck selectedDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.towerDefender.R.layout.activity_navigation);

        featureUnderProductionToast = Toast.makeText(this.getApplicationContext(), "Sorry, feature still under production.", Toast.LENGTH_SHORT);
    }

    /**
     * Launches the single player game (or produces a toast stating the feature is under production)
     * @param view the provided view
     */
    public void launchSinglePlayer(View view){
        //TODO: Open up a practice game
        featureUnderProductionToast.show();
    }

    public void launchMultiplayer(final View view){
        VolleyUtilities.getRequest(getApplicationContext(), "http://coms-309-ss-5.misc.iastate.edu:8080/users/" + UserUtility.getUserId() + " /deck", new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(Object response) {
                Log.e("deck", response.toString());
                decks = new ArrayList<>(JsonUtility.jsonToOwnedDecksArray(response.toString()));

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.deck_selection_view, null);
                mPopupWindow = new PopupWindow(
                        customView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                deck1 = customView.findViewById(R.id.select_deck_1);
                deck2 = customView.findViewById(R.id.select_deck_2);
                deck3 = customView.findViewById(R.id.select_deck_3);
                deck1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                        if(decks.size() > 0) {
                            selectedDeck = decks.get(0);
                        } else {
                            selectedDeck = CardUtilities.getTestDeck();
                        }

                        Intent intent = new Intent(getApplicationContext(), MultiplayerGameActivity.class);
                        startActivity(intent);
                    }
                });
                deck2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                        if(decks.size() > 1) {
                            selectedDeck = decks.get(1);
                        } else {
                            selectedDeck = CardUtilities.getTestDeck();
                        }
                        Intent intent = new Intent(getApplicationContext(), MultiplayerGameActivity.class);
                        startActivity(intent);
                    }
                });
                deck3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                        if(decks.size() > 2) {
                            selectedDeck = decks.get(2);
                        } else {
                            selectedDeck = CardUtilities.getTestDeck();
                        }
                        Intent intent = new Intent(getApplicationContext(), MultiplayerGameActivity.class);
                        startActivity(intent);
                    }
                });
                mPopupWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
            }});
    }

    /**
     * Launches the inventory page(or produces a toast stating the feature is under production)
     * @param view the provided view
     */
    public void openInventory(View view){
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the leaderboard page (or produces a toast stating the feature is under production)
     * @param view the provided view
     */
    public void openLeaderboard(View view){
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the settings page (or produces a toast stating the feature is under production)
     * @param view the provided view
     */
    public void openSettings(View view){
        //TODO: open up settings page
        featureUnderProductionToast.show();
    }
}
