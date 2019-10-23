package com.pvptowerdefense.test;

import com.pvptowerdefense.test.websocketclient.Message;
import com.pvptowerdefense.test.websocketclient.SS5WebSocketClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.PlayedCard;

import java.io.IOException;
import java.util.List;

public class SocketTests {
	@Test
	public void connectToSocketTest() throws IOException {
		SS5WebSocketClient webSocket1 = new SS5WebSocketClient("1");
		SS5WebSocketClient webSocket2 = new SS5WebSocketClient("2");
		SS5WebSocketClient webSocket3 = new SS5WebSocketClient("3");
		SS5WebSocketClient webSocket4 = new SS5WebSocketClient("4");

		Assertions.assertAll(
				() -> Assertions.assertTrue(webSocket1.waitForConnection()),
				() -> Assertions.assertTrue(webSocket2.waitForConnection()),
				() -> Assertions.assertTrue(webSocket3.waitForConnection()),
				() -> Assertions.assertTrue(webSocket4.waitForConnection()));

		Assertions.assertAll(
				() -> Assertions.assertTrue(webSocket1.isOpen()),
				() -> Assertions.assertTrue(webSocket2.isOpen()),
				() -> Assertions.assertTrue(webSocket3.isOpen()),
				() -> Assertions.assertTrue(webSocket4.isOpen())
		);

		webSocket1.close();
		webSocket2.close();
		webSocket3.close();
		webSocket4.close();

		Assertions.assertAll(
				() -> Assertions.assertTrue(webSocket1.isClosed()),
				() -> Assertions.assertTrue(webSocket2.isClosed()),
				() -> Assertions.assertTrue(webSocket3.isClosed()),
				() -> Assertions.assertTrue(webSocket4.isClosed())
		);
	}

	@Test
	public void sendCardByEnemyTowerTest() throws InterruptedException,
			IOException {
		SS5WebSocketClient webSocket1 = new SS5WebSocketClient("1");
		Thread.sleep(1000);
		SS5WebSocketClient webSocket2 = new SS5WebSocketClient("2");

		webSocket1.sendMessage(new PlayedCard("Card1", "Card1", 1, 1, 100, 0,
				"UNIT", 250, 1900, 100, "1"));
		webSocket2.sendMessage(new PlayedCard("Card2", "Card2", 1, 1, 100, 0,
				"UNIT", 250, 100, 100, "2"));
		Thread.sleep(5000);

		List<PlayedCard> cards1 = Message.convertToListOfPlayedCards(
						webSocket1.getMessages().get(8));
		List<PlayedCard> cards2 = Message.convertToListOfPlayedCards(
						webSocket2.getMessages().get(8));

		System.out.println(cards1.toString());
		System.out.println(cards2.toString());

		Assertions.assertAll(
				() -> Assertions.assertTrue(cards1.toString().contains(
						"Card1")),
				() -> Assertions.assertTrue(cards1.toString().contains(
						"Card2")),
				() -> Assertions.assertTrue(cards2.toString().contains(
						"Card1")),
				() -> Assertions.assertTrue(cards2.toString().contains(
						"Card2")),
				() -> Assertions.assertTrue(cards1.toString().contains(
						"Tower1")),
				() -> Assertions.assertTrue(cards1.toString().contains(
						"Tower2")),
				() -> Assertions.assertTrue(cards2.toString().contains(
						"Tower1")),
				() -> Assertions.assertTrue(cards2.toString().contains(
						"Tower3")),
				() -> Assertions.assertEquals(100,
						cards1.get(3).getHitPoints()),
				() -> Assertions.assertEquals(100,
						cards2.get(3).getHitPoints()),
				() -> Assertions.assertEquals(100,
						cards1.get(7).getHitPoints()),
				() -> Assertions.assertEquals(100,
						cards2.get(7).getHitPoints())
		);

		webSocket1.close();
		webSocket2.close();
	}

	@Test
	public void sendCardByOwnTowerTest() throws InterruptedException,
			IOException {
		SS5WebSocketClient webSocket1 = new SS5WebSocketClient("1");
		Thread.sleep(1000);
		SS5WebSocketClient webSocket2 = new SS5WebSocketClient("2");

		webSocket1.sendMessage(new PlayedCard("Card1", "Card1", 1, 1, 1000, 0,
				"UNIT", 250, 100, 100, "1"));
		webSocket2.sendMessage(new PlayedCard("Card2", "Card2", 1, 1, 1000, 0,
				"UNIT", 250, 1900, 100, "2"));
		Thread.sleep(2000);

//		String socket1Messages = webSocket1.getMessages().toString();
//		String socket2Messages = webSocket2.getMessages().toString();

//		Assertions.assertAll(
//				() -> Assertions.assertTrue(socket1Messages.contains(
//						"Card1")),
//				() -> Assertions.assertTrue(socket1Messages.contains(
//						"Card2")),
//				() -> Assertions.assertTrue(socket2Messages.contains(
//						"Card1")),
//				() -> Assertions.assertTrue(socket2Messages.contains(
//						"Card2")),
//				() -> Assertions.assertTrue(socket1Messages.contains(
//						"Tower1")),
//				() -> Assertions.assertTrue(socket1Messages.contains(
//						"Tower2")),
//				() -> Assertions.assertTrue(socket2Messages.contains(
//						"Tower1")),
//				() -> Assertions.assertTrue(socket2Messages.contains(
//						"Tower3"))
//		);

		List<PlayedCard> cards1 =
				Message.convertToListOfPlayedCards(webSocket1.getMessages().get(8));
		List<PlayedCard> cards2 =
				Message.convertToListOfPlayedCards(webSocket2.getMessages().get(8));

		System.out.println(cards1.toString());

		Assertions.assertAll(
				() -> Assertions.assertTrue(1000 <=
						cards1.get(3).getHitPoints()),
				() -> Assertions.assertTrue(1000 <=
						cards2.get(3).getHitPoints()),
				() -> Assertions.assertTrue(1000 <=
						cards1.get(7).getHitPoints()),
				() -> Assertions.assertTrue(1000 <=
						cards2.get(7).getHitPoints())
		);

		webSocket1.close();
		webSocket2.close();
	}
}
