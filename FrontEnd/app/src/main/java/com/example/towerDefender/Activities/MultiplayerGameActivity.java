package com.example.towerDefender.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.SocketServices.Socket;
import com.example.towerDefender.SocketServices.WebSocketClientConnection;
import com.example.towerDefender.VolleyServices.CardRestServices;
import com.example.towerDefender.Game.GameView;
import com.example.towerDefender.VolleyServices.JsonUtils;
import com.example.towerDefender.Game.Player;
import com.example.towerDefender.VolleyServices.VolleyResponseListener;
import com.example.towerDefender.VolleyServices.VolleyUtilities;

import java.util.ArrayList;

import static android.provider.Settings.Secure.ANDROID_ID;

public class MultiplayerGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

  /*
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
*/
    }

    public void startGame(ArrayList<Card> cards){
        setContentView(new GameView(this, new Player(this.getApplicationContext(), cards)));
    }
}
