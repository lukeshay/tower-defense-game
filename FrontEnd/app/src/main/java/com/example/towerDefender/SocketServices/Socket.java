package com.example.towerDefender.SocketServices;

import java.io.IOException;

public class Socket  {

    public static void main(String[] args) throws InterruptedException, IOException {
        WebSocketClientConnection test1 = new WebSocketClientConnection("123456");
       System.out.println(test1.waitForConnection());
       test1.sendMessage("hello  from socket 123456");
       while(true){
           System.out.println(test1.getMessages());
       }
    }
}
