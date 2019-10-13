package com.pvptowerdefense.test.websocketclient;

import com.pvptowerdefense.test.queue.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

/**
 * The type My web socket client.
 */
@ClientEndpoint
public class SS5WebSocketClient {
	/**
	 * This is the server url. For testing locally the url is
	 * 'http://localhost:8080/socket/%s'. For testing on the server the url
	 * is 'ws://coms-309-ss-5.misc.iastate.edu:8080/socket/%s'.
	 */
	private static final String serverUrl = "ws://localhost:8080/socket/%s";

	private String id = null;
	private Queue<String> messages = null;
	private Session session = null;

	private static Logger logger =
			LoggerFactory.getLogger(SS5WebSocketClient.class);

	/**
	 * Instantiates a new My web socket client.
	 *
	 * @param userId the socket id
	 */
	public SS5WebSocketClient(String userId) {
		this.id = userId;
		messages = new Queue<>();

		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, new URI(String.format(serverUrl,
					userId)));
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
		messages.enqueue(String.format("Connected: %s%s", serverUrl, id));
		setSession(session);
	}

	/**
	 * On message.
	 *
	 * @param message the message
	 */
	@OnMessage
	public void onMessage(String message) {
		logger.info("Message " + id);
		messages.enqueue(String.format("Message: %s", message));
	}

	/**
	 * On close.
	 *
	 * @param session the session
	 * @param reason  the reason
	 */
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		messages.enqueue(String.format("Closed: %s", reason.getReasonPhrase()));
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
		messages.enqueue(String.format("Error: %s", throwable.getMessage()));
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
	 * Gets server url.
	 *
	 * @return the server url
	 */
	public static String getServerUrl() {
		return serverUrl;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets messages.
	 *
	 * @return the messages
	 */
	public Queue<String> getMessages() {
		return messages;
	}

	/**
	 * Sets messages.
	 *
	 * @param messages the messages
	 */
	public void setMessages(Queue<String> messages) {
		this.messages = messages;
	}

	/**
	 * Gets session.
	 *
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * Sets session.
	 *
	 * @param session the session
	 */
	public void setSession(Session session) {
		this.session = session;
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
