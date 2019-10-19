package com.example.towerDefender.SocketServices;

import android.util.Log;

import junit.framework.TestCase;

import org.java_websocket.handshake.ServerHandshake;
import org.junit.Assert;

import java.io.IOException;

public class WebSocketClientConnectionTest extends TestCase {

    public void testSocketConnects() {
        SocketUtilities.connectForTest("ws://coms-309-ss-5.misc.iastate.edu:8080/socket/%s", new SocketListener() {

            @Override
            public void onMessage(String message) {
                Log.i("SOCKET_MESSAGE", "Got a message: " + message);
                SocketUtilities.setLastMessage(message);
            }

            @Override
            public void onOpen(ServerHandshake handshake) {
                Log.i("SOCKET_MESSAGE", "opened connection");
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i("SOCKET_MESSAGE", "closed connection");
            }

            @Override
            public void onError(Exception e) {
                Log.e("ERROR", "Encountered an exception: " + e.getMessage());
                e.printStackTrace();
            }

        });
        try{
            Thread.sleep(5000);
        } catch(Exception e){
            
        }
        Assert.assertTrue(SocketUtilities.isOpen());
        Assert.assertTrue(SocketUtilities.getLastMessage().contains("connected=true"));
    }
   }
