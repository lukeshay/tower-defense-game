package com.example.towerDefender.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Game.MainThread;
import com.example.towerDefender.R;
import com.example.towerDefender.SocketServices.SocketListener;
import com.example.towerDefender.SocketServices.SocketMessage;
import com.example.towerDefender.SocketServices.SocketUtilities;
import com.example.towerDefender.Game.GameView;
import com.example.towerDefender.Game.Player;
import com.example.towerDefender.Util.LoadingScreenUtility;
import com.example.towerDefender.Util.SocketMessageHandler;


import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;

import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;

public class MultiplayerGameActivity extends AppCompatActivity {

    private boolean inGame = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.loading_screen);
        ((TextView)findViewById(R.id.toolTip)).setText(LoadingScreenUtility.getToolTip());
        startGame(NavigationActivity.selectedDeck.get_deck());
    }

    /**
     * Launches the game, with the provided cards as the starting deck
     * @param cards the cards with which the user will start the game
     */
    public void startGame(ArrayList<Card> cards){
        final Context ctx = this.getApplicationContext();
        final ArrayList<Card> passed = cards;
        final GameView gameView = new GameView(this, ctx, new Player(ctx, passed));
        SocketUtilities.connect(this.getApplicationContext(), "ws://coms-309-ss-5.misc.iastate.edu:8080/socket/%s", new SocketListener() {
            @OnMessage
            @Override
            public void onMessage(String message) {
                if(!inGame){
                    if(message.contains("\"matchUp\":\"true\"")){
                        try {
                            inGame = true;
                            MultiplayerGameActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setContentView(gameView);
                                }
                            });
                        }
                        catch(Exception e){e.printStackTrace();}
                    }
                } else {
                    SocketMessageHandler.handleMessage(message);
                }
            }


            @Override
            public void onOpen(ServerHandshake handshake) {
                Log.i("SOCKET_INFO", "Connected. Handshake status: \"" + handshake.getHttpStatusMessage() + "\"");

                }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i("SOCKET_INFO", "Socket closed. Reason: \"" + reason + "\"");
                inGame = false;
            }

            @Override
            public void onError(Exception e) {
                Log.i("SOCKET_INFO", "Socket error: " + e.getMessage());
            }
        });
    }
}
