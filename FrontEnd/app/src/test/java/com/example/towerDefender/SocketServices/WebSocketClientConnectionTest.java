package com.example.towerDefender.SocketServices;

import com.example.towerDefender.SocketServices.WebSocketClientConnection;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import javax.websocket.EncodeException;

public class WebSocketClientConnectionTest {

    public static void main(String[] args) throws IOException{
        WebSocketClientConnection connection = new WebSocketClientConnection("1");
        connection.waitForConnection();
        while(true){
           //connection.sendMessage("hi");
        }
    }

    @Test
    public void testSocketConnects(){
        try{
            WebSocketClientConnection connection = new WebSocketClientConnection("1");
            WebSocketClientConnection connection2 = new WebSocketClientConnection("2");
            WebSocketClientConnection connection3 = new WebSocketClientConnection("3");
            WebSocketClientConnection connection4 = new WebSocketClientConnection("4");
            Assert.assertTrue(connection.waitForConnection());
            Assert.assertTrue(connection2.waitForConnection());
            Assert.assertTrue(connection3.waitForConnection());
            Assert.assertTrue(connection4.waitForConnection());
            Assert.assertTrue(connection.isOpen());
            Assert.assertTrue(connection2.isOpen());
            Assert.assertTrue(connection3.isOpen());
            Assert.assertTrue(connection4.isOpen());
            connection.sendMessage("HELLO FROM SOCKET 1");
            connection2.sendMessage("HELLO FROM SOCKET 2");
            connection3.sendMessage("HELLO FROM SOCKET 3");
            connection4.sendMessage("HELLO FROM SOCKET 4");
            Assert.assertFalse(connection.getMessages().isEmpty());
            Assert.assertFalse(connection2.getMessages().isEmpty());
            Assert.assertFalse(connection3.getMessages().isEmpty());
            Assert.assertFalse(connection4.getMessages().isEmpty());
            System.out.println(connection.getMessages());
            System.out.println(connection2.getMessages());
            System.out.println(connection3.getMessages());
            System.out.println(connection4.getMessages());
            connection2.close();
            connection.close();
            connection3.close();
            connection4.close();
        }catch(IOException e){
            Assert.fail(e.getMessage());
        }catch (EncodeException e){
            Assert.fail(e.getMessage());
        }


    }

}
