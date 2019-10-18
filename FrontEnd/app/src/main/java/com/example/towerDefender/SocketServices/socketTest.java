package com.example.towerDefender.SocketServices;

import org.java_websocket.handshake.ServerHandshake;

public class socketTest {

    public static void main(String[] args){
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
