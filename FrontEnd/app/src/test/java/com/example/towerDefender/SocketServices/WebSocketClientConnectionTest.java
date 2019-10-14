package com.example.towerDefender.SocketServices;

import junit.framework.TestCase;

import org.junit.Assert;

public class WebSocketClientConnectionTest extends TestCase {

    public void testSocketConnects(){
        WebSocketClientConnection connection = new WebSocketClientConnection("1");
        Assert.assertTrue(connection.isOpen());
        try {
            connection.close();
        } catch(Exception e){
            Assert.fail(e.getMessage());
        }
        Assert.assertTrue(connection.isClosed());

    }

}
