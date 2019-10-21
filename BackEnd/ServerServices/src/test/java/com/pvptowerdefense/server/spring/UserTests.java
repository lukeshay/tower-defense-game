package com.pvptowerdefense.server.spring;

import com.pvptowerdefense.server.spring.daos.CardsDao;
import com.pvptowerdefense.server.spring.daos.UsersDao;
import com.pvptowerdefense.server.spring.models.Card;
import com.pvptowerdefense.server.spring.models.User;
import com.pvptowerdefense.server.spring.services.CardsService;
import com.pvptowerdefense.server.spring.services.UsersService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.*;

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
    private List<User> testUserList;

    @BeforeAll
    static void setUrl() {
        url = "http://localhost:" + port + "/users";
    }

    @BeforeEach
    void setup() {
        testUser = new User("1", "TestUser1", "testemail", "TestFN", "TestLN", "User")
        testUserList = new ArrayList<>();
        testUserList.add(testUser);

        UsersDao usersDaoMock = Mockito.mock(UsersDao.class);
        usersServiceMock = new UsersService(usersDaoMock);

        Mockito.when(usersDaoMock.findAll())
                .thenReturn(testUserList);
        Mockito.when(usersDaoMock.existsById(testUser.getPhoneId()))
                .thenReturn(true);

        try {
            usersService.addUserToDb(testUser);
        }
        catch (Exception ignored) {}
    }
}
