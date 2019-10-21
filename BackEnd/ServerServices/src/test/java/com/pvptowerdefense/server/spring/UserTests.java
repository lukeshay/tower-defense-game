package com.pvptowerdefense.server.spring;

import com.pvptowerdefense.server.spring.models.Card;
import com.pvptowerdefense.server.spring.models.User;
import com.pvptowerdefense.server.spring.services.CardsService;
import com.pvptowerdefense.server.spring.services.UsersService;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {
    @LocalServerPort
    private static int port;
    private static String url;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UsersService usersService;

    private UsersService usersServiceMock;

    private User testUser;

    @BeforeAll
    static void setUrl() {
        url = "http://localhost:" + port + "/cards";
    }
}
