package com.example.towerDefender.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.towerDefender.BaseApplication;
import com.example.towerDefender.Game.Player;
import com.example.towerDefender.Util.UserUtility;
import com.example.towerDefender.VolleyServices.User;
import com.example.towerDefender.VolleyServices.UserRestServices;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.towerDefender.R.layout.activity_main);
        if(isFirstRun()){
            Intent intent = new Intent(this, RegisterUserActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);
        }
    }

    private boolean isFirstRun(){
        SharedPreferences prefrences = getPreferences(MODE_PRIVATE);
        boolean firstRun = prefrences.getBoolean("RanBefore", false);
        Log.e("first run:", Boolean.toString(firstRun));
        if(!firstRun){
            //first time
            SharedPreferences.Editor editor = prefrences.edit();
            editor.putBoolean("RanBefore", true);
            editor.apply();
            Log.e("ran:", Boolean.toString(prefrences.getBoolean("RanBefore", false)));
        }
        return !firstRun;
    }

    private boolean isUserPresent(){
        User user = UserRestServices.getUser(BaseApplication.getAppContext(), UserUtility.getUserId());
        return user != null;
    }
}
