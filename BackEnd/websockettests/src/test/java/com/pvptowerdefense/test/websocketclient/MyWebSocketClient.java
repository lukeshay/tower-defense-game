package com.pvptowerdefense.test.websocketclient;

import com.pvptowerdefense.test.queue.Queue;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * The type My web socket client.
 */
public class MyWebSocketClient extends WebSocketClient {
	private String socketId;
	private String serverUrl;

	Queue<String> messages;

	/**
	 * Instantiates a new My web socket client.
	 *
	 * @param serverUrl the server uri
	 * @param socketId  the socket id
	 * @throws URISyntaxException the uri syntax exception
	 */
	public MyWebSocketClient(String serverUrl, String socketId) throws URISyntaxException {
		super(new URI(serverUrl + socketId));

		this.socketId = socketId;
		this.serverUrl = serverUrl;

		messages = new Queue<>();

		connect();
		waitForConnected();
	}

	public void onOpen(ServerHandshake serverHandshake) {
		messages.enqueue(String.format("Connected to %s%s", serverUrl, socketId));
	}

	public void onMessage(String s) {
		messages.enqueue("Message: " + s);
	}

	public void onClose(int i, String s, boolean b) {
		messages.enqueue("Closed");
	}

	public void onError(Exception e) {
		messages.enqueue("Error");
	}

	public Queue<String> getMessages() {
		return messages;
	}

	private void waitForConnected() {
		while(!isOpen()) {
			nap();
		}
	}

	private void nap() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
