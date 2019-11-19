package com.example.towerDefender.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NavigationActivity extends AppCompatActivity {
    Toast featureUnderProductionToast;

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

    /**
     * Launches the multiplayer game (or produces a toast stating the feature is under production)
     * @param view the provided view
     */
    public void launchMultiplayer(View view){
        Intent intent = new Intent(this, MultiplayerGameActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the inventory page(or produces a toast stating the feature is under production)
     * @param view the provided view
     */
    public void openInventory(View view){
        Intent intent = new Intent(this, inventoryActivity.class);
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
