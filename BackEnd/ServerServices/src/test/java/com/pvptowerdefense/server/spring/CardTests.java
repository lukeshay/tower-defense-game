package com.pvptowerdefense.server.spring;

import com.pvptowerdefense.server.spring.daos.CardsDao;
import com.pvptowerdefense.server.spring.models.Card;
import com.pvptowerdefense.server.spring.services.CardsService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardTests {
	@LocalServerPort
	private static int port;
	private static String url;

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private CardsService cardsService;

	private CardsService cardsServiceMock;

	private Card testCard;

	@BeforeAll
	static void setUrl() {
		url = "http://localhost:" + port + "/cards";
	}

	@BeforeEach
	void addCard() {
		testCard = new Card("Test Card", "Test Card desc",
				100, 100, 100, 100, "UNIT",
				100);

		CardsDao cardsDaoMock = Mockito.mock(CardsDao.class);
		cardsServiceMock = new CardsService(cardsDaoMock);

		Mockito.when(cardsDaoMock.getCardByName(testCard.getName()))
				.thenReturn(testCard);
		Mockito.when(cardsDaoMock.existsById(testCard.getName()))
				.thenReturn(true);

		try {
			cardsService.addCard(testCard);
		}
		catch (Exception ignored) {}
	}

	@AfterEach
	void removeCard() {
		try{
			cardsService.deleteCard(testCard.getName());
		}
		catch (Exception ignored) {}
	}

	@Test
	void deleteCardTest() {
		Card testCardGet = cardsService.getCardByName(testCard.getName());

		Assertions.assertNotNull(testCardGet);
		Assertions.assertAll(
				() -> Assertions.assertEquals(testCard.getName(),
						testCardGet.getName()),
				() -> Assertions.assertEquals(testCard.getDescription(),
						testCardGet.getDescription()),
				() -> Assertions.assertEquals(testCard.getCost(),
						testCardGet.getCost()),
				() -> Assertions.assertEquals(testCard.getDamage(),
						testCardGet.getDamage()),
				() -> Assertions.assertEquals(testCard.getHitPoints(),
						testCardGet.getHitPoints()),
				() -> Assertions.assertEquals(testCard.getSpeed(),
						testCardGet.getSpeed()),
				() -> Assertions.assertEquals(testCard.getType(),
						testCardGet.getType()),
				() -> Assertions.assertEquals(testCard.getRange(),
						testCardGet.getRange())

		);

		cardsService.deleteCard(testCard.getName());
		Card getDeletedCard = cardsService.getCardByName(testCard.getName());

		Assertions.assertNull(getDeletedCard);
	}

	@Test
	void updateCardTest() {
		Card testCardGet = cardsService.getCardByName(testCard.getName());

		Assertions.assertNotNull(testCardGet);
		Assertions.assertAll(
				() -> Assertions.assertEquals(testCard.getName(),
						testCardGet.getName()),
				() -> Assertions.assertEquals(testCard.getDescription(),
						testCardGet.getDescription()),
				() -> Assertions.assertEquals(testCard.getCost(),
						testCardGet.getCost()),
				() -> Assertions.assertEquals(testCard.getDamage(),
						testCardGet.getDamage()),
				() -> Assertions.assertEquals(testCard.getHitPoints(),
						testCardGet.getHitPoints()),
				() -> Assertions.assertEquals(testCard.getSpeed(),
						testCardGet.getSpeed()),
				() -> Assertions.assertEquals(testCard.getType(),
						testCardGet.getType()),
				() -> Assertions.assertEquals(testCard.getRange(),
						testCardGet.getRange())
		);

		testCard.setDamage(100000);
		cardsService.updateCard(testCard);
		Card testCardGet2 = cardsService.getCardByName(testCard.getName());

		Assertions.assertNotNull(testCardGet);
		Assertions.assertAll(
				() -> Assertions.assertEquals(testCard.getName(),
						testCardGet2.getName()),
				() -> Assertions.assertEquals(testCard.getDescription(),
						testCardGet2.getDescription()),
				() -> Assertions.assertEquals(testCard.getCost(),
						testCardGet2.getCost()),
				() -> Assertions.assertEquals(testCard.getDamage(),
						testCardGet2.getDamage()),
				() -> Assertions.assertEquals(testCard.getHitPoints(),
						testCardGet2.getHitPoints()),
				() -> Assertions.assertEquals(testCard.getSpeed(),
						testCardGet2.getSpeed()),
				() -> Assertions.assertEquals(testCard.getType(),
						testCardGet2.getType()),
				() -> Assertions.assertEquals(testCard.getRange(),
						testCardGet2.getRange())
		);
	}

	@Test
	void getCardTestMock() {
		Card getCardMock = cardsServiceMock.getCardByName(testCard.getName());

		Assertions.assertNotNull(getCardMock);
		Assertions.assertAll(
				() -> Assertions.assertEquals(testCard.getName(),
						getCardMock.getName()),
				() -> Assertions.assertEquals(testCard.getDescription(),
						getCardMock.getDescription()),
				() -> Assertions.assertEquals(testCard.getCost(),
						getCardMock.getCost()),
				() -> Assertions.assertEquals(testCard.getDamage(),
						getCardMock.getDamage()),
				() -> Assertions.assertEquals(testCard.getHitPoints(),
						getCardMock.getHitPoints()),
				() -> Assertions.assertEquals(testCard.getSpeed(),
						getCardMock.getSpeed()),
				() -> Assertions.assertEquals(testCard.getType(),
						getCardMock.getType()),
				() -> Assertions.assertEquals(testCard.getRange(),
						getCardMock.getRange())
		);
	}
}
