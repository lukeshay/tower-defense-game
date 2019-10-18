//package com.example.towerDefender.SocketServices;
//
//import com.example.towerDefender.SocketServices.WebSocketClientConnection;
//import junit.framework.TestCase;
//import org.junit.Assert;
//
//import java.io.IOException;
//
//public class WebSocketClientConnectionTest extends TestCase {
//
//    public void testSocketConnects(){
//        try{
//            WebSocketClientConnection connection = new WebSocketClientConnection("1");
//            WebSocketClientConnection connection2 = new WebSocketClientConnection("2");
//            WebSocketClientConnection connection3 = new WebSocketClientConnection("3");
//            WebSocketClientConnection connection4 = new WebSocketClientConnection("4");
//            Assert.assertTrue(connection.waitForConnection());
//            Assert.assertTrue(connection2.waitForConnection());
//            Assert.assertTrue(connection3.waitForConnection());
//            Assert.assertTrue(connection4.waitForConnection());
//            Assert.assertTrue(connection.isOpen());
//            Assert.assertTrue(connection2.isOpen());
//            Assert.assertTrue(connection3.isOpen());
//            Assert.assertTrue(connection4.isOpen());
//            connection.sendMessage("HELLO FROM SOCKET 1");
//            connection2.sendMessage("HELLO FROM SOCKET 2");
//            connection3.sendMessage("HELLO FROM SOCKET 3");
//            connection4.sendMessage("HELLO FROM SOCKET 4");
//            Assert.assertFalse(connection.getMessages().isEmpty());
//            Assert.assertFalse(connection2.getMessages().isEmpty());
//            Assert.assertFalse(connection3.getMessages().isEmpty());
//            Assert.assertFalse(connection4.getMessages().isEmpty());
//            connection2.close();
//            connection.close();
//            connection3.close();
//            connection4.close();
//        }catch(IOException e){
//            Assert.fail(e.getMessage());
//        }
//
//
//    }
//
//}
