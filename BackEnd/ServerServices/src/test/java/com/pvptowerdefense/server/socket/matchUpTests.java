package com.pvptowerdefense.server.socket;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import javax.websocket.Session;
import java.util.concurrent.Future;

public class matchUpTests {
	private Session session;

	@BeforeEach
	void setup() {
		Mockito.when(session.getAsyncRemote().sendText(null));
	}
}
