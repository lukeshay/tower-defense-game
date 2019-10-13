package com.example.towerDefender.SocketServices;

import java.io.IOException;

public class Socket  {

    public static void main(String[] args) throws InterruptedException, IOException {
        WebSocketClientConnection test1 = new WebSocketClientConnection("1");
       System.out.println(test1.waitForConnection());
            test1.sendMessage("this is from test 1");
            Thread.sleep(1000);
            System.out.println(test1.getMessages().toString());
            test1.close();
    }
}
