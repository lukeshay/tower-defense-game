package com.example.towerDefender.SocketServices;

import android.content.Context;
import android.provider.Settings;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import static android.provider.Settings.Secure.ANDROID_ID;

public class SocketUtilities {
    private static WebSocketClient cc;

    private static boolean initalized = false;

    public static void sendMessage(String message){
        if(initalized) {
            cc.send(message);
        }
    }

    public static void connect(Context context, String url, final SocketListener listener) {
        Draft[] drafts = {new Draft_6455()};
        try {
            cc = new WebSocketClient(new URI(String.format(url, Settings.Secure.getString(context.getContentResolver(), ANDROID_ID))), (Draft) drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    initalized = true;
                    listener.onOpen(serverHandshake);
                }

                @Override
                public void onMessage(String s) {
                    listener.onMessage(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    initalized = false;
                    listener.onClose(i,s,b);
                }

                @Override
                public void onError(Exception e) {
                    listener.onError(e);
                }
            };
            cc.connect();
        } catch(Exception e){e.printStackTrace();}
    }

}
