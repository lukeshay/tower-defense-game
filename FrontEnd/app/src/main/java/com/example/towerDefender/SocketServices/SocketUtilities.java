package com.example.towerDefender.SocketServices;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Random;


import static android.provider.Settings.Secure.ANDROID_ID;

public class SocketUtilities {
    private static WebSocketClient webSocketClient;
    private static boolean initalized = false;
    //mostly just used for testing
    private static String lastMessage = "";

    /**
     * Sends the provided message over the socket.
     * @param message the message to send
     */
    public static void sendMessage(String message){
        try {
            if(initalized) {
                webSocketClient.send(message);
            }
        } catch(Exception e){

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
            try{
                webSocketClient.close();
            } catch (Exception e){
                Log.e("ERROR", "error closing socket.");
            }

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

    /**
     * Connects to the socket using a dynamically generated player id
     * @param context the context the socket is used in
     * @param url the url the socket will connect to
     * @param listener the {@link SocketListener} to use
     */
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
            initalized = true;
        } catch(Exception e){e.printStackTrace();}
    }

    /**
     * Connects to the socket with a hardcoded player id. Only for use in testing
     * @param url the url to connect to
     * @param listener the {@link SocketListener} to use
     */
    public static void connectForTest(String url, final SocketListener listener) {
        Draft[] drafts = {new Draft_6455()};
        try {
            webSocketClient = new WebSocketClient(new URI(String.format(url, "testSocket")), (Draft) drafts[0]) {
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

    /**
     * A testing method to make sure that the socket is receiving messages.
     * This method should only be called if the {@link SocketListener} used to connect to the socket updates lastMessage with each call to onMessage().
     * @return the last message received over the socket connection
     */
    public static String getLastMessage(){
        return lastMessage;
    }

    public static void setLastMessage(String message){
        lastMessage = message;
    }
}
