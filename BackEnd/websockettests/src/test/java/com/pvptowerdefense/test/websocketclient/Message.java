package com.pvptowerdefense.test.websocketclient;

import com.google.gson.reflect.TypeToken;
import shared.PlayedCard;
import java.util.List;
import com.google.gson.Gson;
import shared.SocketMessage;

public class Message {

	public static String convertToJson(Object message) {
		Gson g = new Gson();

		return g.toJson(message);
	}

	public static List<PlayedCard> convertToListOfPlayedCards(String message) {
		Gson g = new Gson();
		return g.fromJson(message,
				new TypeToken<List<PlayedCard>>(){}.getType());
	}

	public static SocketMessage convertToSocketMessage(String message) {
		return new Gson().fromJson(message, SocketMessage.class);
	}

}
