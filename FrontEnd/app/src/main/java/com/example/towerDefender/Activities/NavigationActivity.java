package com.example.towerDefender.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.towerDefender.R.layout.activity_navigation);
    }

    public void launchSinglePlayer(View view){
        //TODO: Open up a practice game
    }

    public void launchMultiplayer(View view){
        Intent intent = new Intent(this, test.class);
        startActivity(intent);
    }

    public void openInventory(View view){
        Intent intent = new Intent(this, inventoryActivity.class);
        startActivity(intent);
    }
}
