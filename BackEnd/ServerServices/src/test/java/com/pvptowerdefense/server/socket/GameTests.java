package com.pvptowerdefense.server.socket;

import com.pvptowerdefense.server.socket.models.Game;
import com.pvptowerdefense.server.socket.models.PlayedCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GameTests {
	private static final int TEN_CYCLES = 10;
	private static final int TWENTY_CYCLES = 20;
	private static final int FIVE_HUNDRED_CYCLES = 500;

	private Game game;
	private PlayedCard p1Card;
	private PlayedCard p2Card;

	@BeforeEach
	void setup() {
		game = new Game("1", "2");
		p1Card = new PlayedCard("Card1", "Card1", 1, 1, 100, 10,
				"UNIT", 100, 100, 100, "1");
		p2Card = new PlayedCard("Card2", "Card2", 1, 1, 100, 10,
				"UNIT", 100, 1900, 100, "2");
	}

	@Test
	void cycleGameOnlyTowersTest() {
		List<PlayedCard> preCycle = game.getCards();

		for (int i = 0; i < TEN_CYCLES; i++) {
			game.clockCycle();
		}

		List<PlayedCard> postCycle = game.getCards();

		Assertions.assertIterableEquals(preCycle, postCycle, "The cards " +
				"should not change but did.");
	}

	@Test
	void cycleGameCardsByAllyTowerTest() throws CloneNotSupportedException {
		game.addCard(p1Card.clone());
		game.addCard(p2Card.clone());

		for (int i = 0; i < TEN_CYCLES; i++) {
			game.clockCycle();
		}

		PlayedCard p1CardPostCycle = game.getCards()
				.stream()
				.filter(e -> e.getName().equals("Card1"))
				.findFirst()
				.get();
		PlayedCard p2CardPostCycle = game.getCards()
				.stream()
				.filter(e -> e.getName().equals("Card2"))
				.findFirst()
				.get();

		Assertions.assertAll(
				() -> Assertions.assertEquals(
						p1Card.getxValue() + (p1Card.getSpeed() * TEN_CYCLES),
						p1CardPostCycle.getxValue(),
						"Player ones card did not move as expected."),

				() -> Assertions.assertEquals(
						p2Card.getxValue() - (p2Card.getSpeed() * TEN_CYCLES),
						p2CardPostCycle.getxValue(),
						"Player twos card did not move as expected."),

				() -> Assertions.assertEquals(
						p1Card.getHitPoints(), p1CardPostCycle.getHitPoints(),
						"Player one card lost health but should not have"),

				() -> Assertions.assertEquals(
						p2Card.getHitPoints(),
						p2CardPostCycle.getHitPoints(),
						"Player two card lost health but should not have")
		);
	}

	@Test
	void cycleGameCardsByEnemyTowerTest() throws CloneNotSupportedException {
		p1Card.setxValue(1600);
		p2Card.setxValue(100);

		game.addCard(p1Card.clone());
		game.addCard(p2Card.clone());

		for (int i = 0; i < FIVE_HUNDRED_CYCLES; i++) {
			game.clockCycle();
		}

		PlayedCard p1CardPostCycle = game.getCards()
				.stream()
				.filter(e -> e.getName().equals("Card1"))
				.findFirst()
				.get();
		PlayedCard p2CardPostCycle = game.getCards()
				.stream()
				.filter(e -> e.getName().equals("Card2"))
				.findFirst()
				.get();

		Assertions.assertAll(
				() -> Assertions.assertEquals(
						p1Card.getxValue(),
						p1CardPostCycle.getxValue(),
						"Player ones card did not stay still."),

				() -> Assertions.assertEquals(
						p2Card.getxValue(),
						p2CardPostCycle.getxValue(),
						"Player twos card did not stay still."),

				() -> Assertions.assertEquals(
						p1Card.getHitPoints() - ((FIVE_HUNDRED_CYCLES / 60 + 1) * 2),
						p1CardPostCycle.getHitPoints(),
						"Player one card lost health but should not have"),

				() -> Assertions.assertEquals(
						p2Card.getHitPoints() - ((FIVE_HUNDRED_CYCLES / 60 + 1) * 2),
						p2CardPostCycle.getHitPoints(),
						"Player two card lost health but should not have")
		);
	}
}
