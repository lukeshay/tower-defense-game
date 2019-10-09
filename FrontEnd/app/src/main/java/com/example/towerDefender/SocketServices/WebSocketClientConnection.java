package com.example.towerDefender.SocketServices;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;

public class WebSocketClientConnection {
    private static final String serverUrl = "ws://coms-309-ss-5.misc.iastate.edu:8080/socket/%s";
    //private static final String serverUrl= "http://localhost:8080/socket/%s";
    private WebSocketClient connection;
    public WebSocketClientConnection(String userId){
        try{
            connection = new WebSocketClient(new URI(String.format(serverUrl, userId)), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println(serverHandshake.getHttpStatusMessage());
                }

                @Override
                public void onMessage(String s) {
                    try{
                        JSONObject obj = new JSONObject(s);
                        String channel = obj.getString("channel");
                        System.out.println(channel);
                    } catch(Exception e){
                        //do nothing
                    }

                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("closing connection");
                }

                @Override
                public void onError(Exception e) {

                }
            };
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage() + '\n');
        }
    }

    public boolean isOpen(){
        return this.connection.isOpen();
    }

    public void connectToSocket(){
        this.connection.connect();
    }

    public boolean waitForOpenConnection(int secondsToWait){
        long startTime = System.currentTimeMillis();
        float msWaited = 0;
        while(!connection.isOpen() && msWaited < secondsToWait * 1000){
            msWaited = System.currentTimeMillis() - startTime;
        }
        return connection.isOpen();
    }

    public static void main(String[] args){
        WebSocketClientConnection connection = new WebSocketClientConnection("1");
        connection.connectToSocket();
        if(connection.waitForOpenConnection(15)){
            System.out.println("Open connection established.");
        }
    }
}
