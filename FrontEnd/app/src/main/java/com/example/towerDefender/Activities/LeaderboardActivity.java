package com.example.towerDefender.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.towerDefender.Util.LeaderboardUtility;

public class LeaderboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.towerDefender.R.layout.activity_leaderboard);
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

}
