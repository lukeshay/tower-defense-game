package com.pvptowerdefense.server.socket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SocketTests {
	@LocalServerPort
	private int port;

//	@Test
	void connectionTest() throws InterruptedException {
		SS5WebSocketClient client1 = new SS5WebSocketClient("test1", port);
		SS5WebSocketClient client2 = new SS5WebSocketClient("test2", port);

		Thread.sleep(10000);

		Assertions.assertAll(
			() -> Assertions.assertTrue(client1.isOpen(), "Client 1 is not " +
					"connected."),
			() -> Assertions.assertTrue(client2.isOpen(), "Client 2 is not " +
					"connected.")
		);
	}
}
