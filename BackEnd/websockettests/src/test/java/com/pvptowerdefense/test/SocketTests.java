package com.pvptowerdefense.test;

import com.pvptowerdefense.test.websocketclient.MyWebSocketClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

public class SocketTests {
	private static final String URL = "http://localhost:8080/socket/";

	@Test
	public void connectToSocketTest() throws URISyntaxException, InterruptedException {
		MyWebSocketClient webSocket = new MyWebSocketClient(URL, "1");

		webSocket.send("####### HELLO #######");

		Assertions.assertTrue(webSocket.isOpen());
		Thread.sleep(10000);

		System.out.println(webSocket.getMessages().toString());
		webSocket.close();
	}
}
