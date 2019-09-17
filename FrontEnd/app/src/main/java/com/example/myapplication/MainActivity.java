package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isFirstRun()){
            Intent intent = new Intent(this, registerUserActivity.class);
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
            editor.commit();
            Log.e("ran:", Boolean.toString(prefrences.getBoolean("RanBefore", false)));
        }
        return !firstRun;
    }
}
