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
    private static WebSocketClient webSocketClient;

    private static boolean initalized = false;

    /**
     * Sends the provided message over the socket.
     * @param message the message to send
     */
    public static void sendMessage(String message){
        if(initalized) {
            webSocketClient.send(message);
        }
    }

    /**
     * @return true if socket connection is open
     */
    public static boolean isOpen(){
        if(initalized){
            return webSocketClient.isOpen();
        } else{
            return false;
        }
    }

    /**
     * Closes the socket connection.
     */
    public static void closeSocket(){
        if(initalized){
            webSocketClient.close();
        }
    }

    /**
     * @return true if the socket connection is closed
     */
    public static boolean isClosed(){
        if(initalized){
            return webSocketClient.isClosed();
        } else{
            return true;
        }
    }


    public static void connect(Context context, String url, final SocketListener listener) {
        Draft[] drafts = {new Draft_6455()};
        try {
            webSocketClient = new WebSocketClient(new URI(String.format(url, Settings.Secure.getString(context.getContentResolver(), ANDROID_ID))), (Draft) drafts[0]) {
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
            webSocketClient.connect();
        } catch(Exception e){e.printStackTrace();}
    }

}
