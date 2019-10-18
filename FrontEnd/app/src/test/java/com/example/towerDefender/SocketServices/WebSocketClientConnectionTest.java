package com.example.towerDefender.SocketServices;

import junit.framework.TestCase;

import org.java_websocket.handshake.ServerHandshake;
import org.junit.Assert;

import java.io.IOException;

public class WebSocketClientConnectionTest extends TestCase {

    public void testSocketConnects() {
        SocketUtilities.connect(null, "ws://coms-309-ss-5.misc.iastate.edu:8080/socket/%s", new SocketListener() {
            @Override
            public void onMessage(String message) {
                System.out.println("get message " + message);
            }

            @Override
            public void onOpen(ServerHandshake handshake) {
                System.out.println("opened");
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("closed");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("error");
            }
        });
    }
   }
