package com.example.towerDefender.SocketServices;

import com.example.towerDefender.SocketServices.WebSocketClientConnection;
import junit.framework.TestCase;
import org.junit.Assert;

public class WebSocketClientConnectionTest extends TestCase {

    public void testSocketConnects(){
        try{
            WebSocketClientConnection connection = new WebSocketClientConnection("1");
            Thread.sleep(10000);
            WebSocketClientConnection connection2 = new WebSocketClientConnection("2");
            Assert.assertTrue(connection.isOpen());
            Assert.assertTrue(connection2.isOpen());
            connection.close();
            Assert.assertTrue(connection.isClosed());
            connection2.close();
            Assert.assertTrue(connection2.isClosed());
        }catch(Exception e){
            Assert.fail();
        }


    }

}
