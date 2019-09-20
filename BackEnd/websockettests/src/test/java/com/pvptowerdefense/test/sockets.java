package com.pvptowerdefense.test;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.enums.Opcode;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Collection;

public class sockets {

	@Test
	public void connectToSocketTest() throws URISyntaxException, InterruptedException {
		WebSocketClient webSocket = new MyWebSocketClient(
				new URI("ws://localhost:8080/socket/1"));

		webSocket.connect();

		webSocket.send("####### HELLO #######");
		Assertions.assertTrue(webSocket.isOpen());
		webSocket.close();
	}

	private class MyWebSocketClient extends WebSocketClient {

		public MyWebSocketClient(URI serverUri) {
			super(serverUri);
		}

		public void onOpen(ServerHandshake serverHandshake) {
			System.out.println("Connected");
		}

		public void onMessage(String s) {
			System.out.println("Message: " + s);
		}

		public void onClose(int i, String s, boolean b) {
			System.out.println("Closed");
		}

		public void onError(Exception e) {
			System.out.println("Error");
		}
	}
}
