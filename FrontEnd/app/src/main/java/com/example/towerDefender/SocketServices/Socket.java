package com.example.towerDefender.SocketServices;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Socket  {

    private static WebSocketClient cc;
    public static void main(String[] args) throws InterruptedException, IOException {
        Draft[] drafts = {new Draft_6455()};


        /**
         * If running this on an android device, make sure it is on the same network as your
         * computer, and change the ip address to that of your computer.
         * If running on the emulator, you can use localhost.
         */
        String w = "ws://coms-309-ss-5.misc.iastate.edu:8080/socket/%s";

        try {
            System.out.println("Socket: " + "Trying socket");
            cc = new WebSocketClient(new URI(String.format(w,"1234")),(Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                   System.out.println( "run() returned: " + message);
                    //t1.setText("hello world");
                    //System.out.println("first", "run() returned: " + s);
                    //s=t1.getText().toString();
                    //System.out.println("second", "run() returned: " + s);
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                   System.out.println("OPEN" + " run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("CLOSE" + " onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e)
                {
                    System.out.println("Exception: " + e.toString());
                }
            };
        }
        catch (URISyntaxException e) {
        //    System.out.println("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();

        try {
            cc.send("testing");
            Thread.sleep(1000);
        }
        catch (Exception e)
        {
            System.out.println("ExceptionSendMessage: " + e.getMessage().toString());
        }
    }
}
