package com.pvptowerdefense.test.websocketclient;

import com.pvptowerdefense.test.queue.Queue;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * The type My web socket client.
 */
public class SS5WebSocketClient extends WebSocketClient {
	private static final String serverUrl = "http://coms-309-ss-5.misc" +
			".iastate.edu:8080/socket/";

	private String socketId;
	private Queue<String> messages;

	private static Logger logger =
			LoggerFactory.getLogger(SS5WebSocketClient.class);

	/**
	 * Instantiates a new My web socket client.
	 *
	 * @param userId  the socket id
	 * @throws URISyntaxException the uri syntax exception
	 */
	public SS5WebSocketClient(String userId) throws URISyntaxException {
		super(new URI(serverUrl + userId));

		this.socketId = userId;
		messages = new Queue<>();

		logger.info("Connecting");
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
