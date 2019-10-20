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
                System.out.println("Got a message: " + message);
                SocketUtilities.setLastMessage(message);
            }

            @Override
            public void onOpen(ServerHandshake handshake) {
                System.out.println("opened connection");
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("closed connection");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("Encountered an exception: " + e.getMessage());
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
