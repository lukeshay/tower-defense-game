package com.pvptowerdefense.server.socket.handlers;

import com.pvptowerdefense.server.socket.models.MatchUp;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@ServerEndpoint("/socket/{phoneId}")
public class SocketHandler {
	HashMap<String, Session> idAndSession;
	HashMap<Session, String> sessionAndId;
	List<MatchUp> matchUpList;

	public SocketHandler() {
		idAndSession = new HashMap<>();
		sessionAndId = new HashMap<>();
		matchUpList = new ArrayList<>();
	}

	@OnOpen
	public void onOpen(Session session, @PathVariable String phoneId) {
		idAndSession.put(phoneId, session);
		sessionAndId.put(session, phoneId);
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		MatchUp matchup = findMatchUp(session);
		if (matchup != null) {
			matchup.getOtherSession(session).getAsyncRemote().sendText(message);
		}
	}

	@OnClose
	public void onClose(Session session, @PathVariable String phoneId) {
		idAndSession.remove(phoneId);
		sessionAndId.remove(session);

		matchUpList.remove(findMatchUp(session));
	}

	private MatchUp findMatchUp(Session session) {
		try {
			return matchUpList.stream().filter(getMatchUpPredicate(session))
					.collect(Collectors.toList()).get(0);
		}
		catch (Exception e) {
			return null;
		}
	}

	private Predicate<MatchUp> getMatchUpPredicate(Session session) {
		return matchUp -> matchUp.getPlayerOneSession().equals(session) ||
				matchUp.getPlayerTwoSession().equals(session);
	}
}
