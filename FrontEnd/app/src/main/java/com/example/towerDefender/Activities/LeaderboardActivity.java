package com.example.towerDefender.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.towerDefender.R;
import com.example.towerDefender.Util.LeaderboardUtility;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.towerDefender.R.layout.activity_leaderboard);
        if(NavigationActivity.selectedDeck == null){
            findViewById(R.id.playAgain).setVisibility(View.INVISIBLE);
        }
        LeaderboardUtility.initializeUsers(this);
    }

    /**
     * Launches the navigation page
     * @param view the provided view
     */
    public void returnToNavigation(View view){
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the multiplayer game activity
     * @param view the provided view
     */
    public void playAgain(View view){
        Intent intent = new Intent(this, MultiplayerGameActivity.class);
        startActivity(intent);
    }

}
