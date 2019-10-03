package com.pvptowerdefense.server.spring;

import com.pvptowerdefense.server.spring.daos.CardsDao;
import com.pvptowerdefense.server.spring.models.Card;
import com.pvptowerdefense.server.spring.services.CardsService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardTests {
	@LocalServerPort
	private static int port;
	private static String url;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	CardsService cardsService;

	private Card testCard = new Card("Test Card" ,"Test Card desc", 100,
			100, 100, 100, "UNIT", 100);

	@BeforeAll
	static void setUrl() {
		url = "http://localhost:" + port + "/cards";
	}

	@Test
	@Order(1)
	void addCardTest() {
		restTemplate.postForEntity(url, testCard, String.class);

		Card testCardGet =
				restTemplate.getForEntity(url + "/" + testCard.getName(),
						Card.class).getBody();

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
	}

//	@Test
//	@Order(2)
	void deleteCardTest() {
		restTemplate.delete(url + "/123456/" + testCard.getName());

		ResponseEntity<String> r =
				restTemplate.getForEntity(url + "/" + testCard.getName(),
						String.class);

		Assertions.assertEquals("", Objects.requireNonNull(r.getBody()).trim());
	}
}
