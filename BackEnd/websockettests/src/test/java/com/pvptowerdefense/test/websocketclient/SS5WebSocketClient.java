package com.pvptowerdefense.test.websocketclient;

import com.pvptowerdefense.test.queue.Queue;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The type My web socket client.
 */
@ClientEndpoint
public class SS5WebSocketClient {
	private static final String serverUrl = "http://localhost:8080/socket/%s";

	private String socketId;
	private Queue<String> messages;

	private static Logger logger =
			LoggerFactory.getLogger(SS5WebSocketClient.class);

	private Session userSession = null;

	/**
	 * Instantiates a new My web socket client.
	 *
	 * @param userId  the socket id
	 * @throws URISyntaxException the uri syntax exception
	 */
	public SS5WebSocketClient(String userId) throws URISyntaxException {
		this.socketId = userId;
		messages = new Queue<>();

		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, new URI(String.format(serverUrl,
					userId)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		messages.enqueue(String.format("Connected to %s%s", serverUrl, socketId));
		setUserSession(session);
	}

	@OnMessage
	public void onMessage(String message) {
		messages.enqueue(String.format("Message: %s", message));
	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {
		messages.enqueue(String.format("Closed: %s", reason.getReasonPhrase()));
		this.userSession = null;
	}

	@OnError
	public void onError(Exception e) {
		messages.enqueue("Error");
	}

	public void sendMessage(String message) {
		this.userSession.getAsyncRemote().sendText(message);
	}

	public Queue<String> getMessages() {
		return messages;
	}

	public static String getServerUrl() {
		return serverUrl;
	}

	public String getSocketId() {
		return socketId;
	}

	public void setSocketId(String socketId) {
		this.socketId = socketId;
	}

	public void setMessages(Queue<String> messages) {
		this.messages = messages;
	}

	public Session getUserSession() {
		return userSession;
	}

	public void setUserSession(Session userSession) {
		this.userSession = userSession;
	}

	private void nap() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
