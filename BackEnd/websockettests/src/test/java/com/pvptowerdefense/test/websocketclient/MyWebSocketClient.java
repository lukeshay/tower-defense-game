package com.pvptowerdefense.test.websocketclient;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * The type My web socket client.
 */
public class MyWebSocketClient extends WebSocketClient {
	private String socketId;
	private String serverUrl;

	/**
	 * Instantiates a new My web socket client.
	 *
	 * @param serverUrl the server uri
	 * @param socketId  the socket id
	 */
	public MyWebSocketClient(String serverUrl, String socketId) throws URISyntaxException {
		super(new URI(serverUrl + socketId));

		this.socketId = socketId;
		this.serverUrl = serverUrl;

		connect();
	}

	public void onOpen(ServerHandshake serverHandshake) {
		waitForConnected();
		System.out.println(String.format("Connected to %s%s", serverUrl, socketId));
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

	private void waitForConnected() {
		for (int i = 0; i < 100 && isClosed(); i++) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (isClosed()) {
			throw new WebsocketNotConnectedException();
		}
	}
}
