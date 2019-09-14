package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    public void launchSinglePlayer(View view){
        //TODO: Open up a practice game
    }

    public void launchMultiplayer(View view){
        //TODO: Open up multiplayer game menu
    }

    public void openInventory(View view){
        //TODO: Open up multiplayer game menu
        Intent intent = new Intent(this, inventoryActivity.class);
        startActivity(intent);
    }
}
