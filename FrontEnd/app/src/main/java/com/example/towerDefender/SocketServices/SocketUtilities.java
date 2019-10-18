package com.example.towerDefender.SocketServices;

import android.content.Context;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class SocketUtilities {


    public static void connect(Context context, String url, final SocketListener listener) {
        Draft[] drafts = {new Draft_6455()};
        try {
            WebSocketClient cc = new WebSocketClient(new URI(String.format(url, "123")), (Draft) drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    listener.onOpen(serverHandshake);
                }

                @Override
                public void onMessage(String s) {
                    listener.onMessage(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
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
