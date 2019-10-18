package com.example.towerDefender.SocketServices;

import android.content.Context;

public class CreateSocket {
private static CreateSocket socket;
private static Context ctx;

private CreateSocket(Context context){
    ctx = context;
}

    public static synchronized CreateSocket getInstance(Context context) {
        if (socket == null) {
            socket = new CreateSocket(context);
        }
        return socket;
    }
}
