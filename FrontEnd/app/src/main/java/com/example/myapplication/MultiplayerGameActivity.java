package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.VolleyServices.VolleyResponseListener;
import com.example.myapplication.VolleyServices.VolleyUtilities;

import java.util.ArrayList;

public class MultiplayerGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        VolleyUtilities.getRequest(this.getApplicationContext(), CardRestServices.BASE_URL, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                System.out.println("encountered an error while grabbing cards from database. " + message);
            }

            @Override
            public void onResponse(Object response) {
                startGame(new ArrayList<>(JsonUtils.jsonToCardArray(response.toString())));

            }
        });

    }

    public void startGame(ArrayList<Card> cards){
        setContentView(new GameView(this, new Player(this.getApplicationContext(), cards)));
    }
}
