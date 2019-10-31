package com.pvptowerdefense.server.socket;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * The type My web socket client.
 */
@ClientEndpoint
public class SS5WebSocketClient {
	/**
	 * This is the server url. For testing locally the url is
	 * 'ws://localhost:8080/socket/%s'. For testing on the server the url
	 * is 'ws://coms-309-ss-5.misc.iastate.edu:8080/socket/%s'.
	 */
	private static final String serverUrl = "ws://localhost:%d/socket/%s/";

	private List<String> messages;
	private Session session = null;

	/**
	 * Instantiates a new My web socket client.
	 *
	 * @param userId the socket id
	 */
	public SS5WebSocketClient(String userId, int port) {
		messages = new ArrayList<>();
		System.out.println("####################################");
		System.out.println(String.format(serverUrl,
				port, userId));
		System.out.println("####################################");

		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, new URI(String.format(serverUrl,
					port, userId)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * On open.
	 *
	 * @param session the session
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	/**
	 * On message.
	 *
	 * @param message the message
	 */
	@OnMessage
	public void onMessage(String message) {
		messages.add(message);
	}

	/**
	 * On close.
	 *
	 * @param session the session
	 * @param reason  the reason
	 */
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		messages.add(String.format("Closed: %s", reason.getReasonPhrase()));
		this.session = null;
	}

	/**
	 * On error.
	 *
	 * @param session   the session
	 * @param throwable the throwable
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		messages.add(String.format("Error: %s", throwable.getMessage()));
	}

	/**
	 * Sends message to server.
	 *
	 * @param message the message
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * Closes session.
	 *
	 * @throws IOException the io exception
	 */
	public void close() throws IOException {
		session.close();
		session = null;
	}

	/**
	 * Is open boolean.
	 *
	 * @return the boolean
	 */
	public boolean isOpen() {
		return session.isOpen();
	}

	/**
	 * Is closed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isClosed() {
		return session == null || !session.isOpen();
	}

	/**
	 * Wait for connection boolean.
	 *
	 * @return the boolean
	 */
	public boolean waitForConnection() {
		for (int i = 0; i < 12000 && !isOpen(); i++) {
			nap();
		}

		return isOpen();
	}

	private void nap() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
