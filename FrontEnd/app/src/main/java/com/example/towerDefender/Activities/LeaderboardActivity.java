package com.example.towerDefender.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.towerDefender.Util.LeaderboardUtility;

public class LeaderboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.towerDefender.R.layout.activity_leaderboard);
        LeaderboardUtility.initializeUsers(this);
    }
}
