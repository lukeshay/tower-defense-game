package com.example.towerDefender.SocketServices;

import org.java_websocket.handshake.ServerHandshake;

public interface SocketListener {
    void onMessage(String message);
    void onOpen(ServerHandshake handshake);
    void onClose(int code, String reason, boolean remote);
    void onError(Exception e);
}
