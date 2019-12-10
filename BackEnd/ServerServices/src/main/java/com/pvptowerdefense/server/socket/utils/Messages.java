package com.pvptowerdefense.server.socket.utils;

import com.google.gson.Gson;
import com.pvptowerdefense.server.socket.models.PlayedCard;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Messages.
 */
public class Messages {
	/**
	 * Connected true match up false string.
	 *
	 * @return the string
	 */
	public static String connectedTrueMatchUpFalse() {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "true");
		map.put("matchUp", "false");
		map.put("side", "");

		return new Gson().toJson(map);
	}

	/**
	 * Connected true match up true string.
	 *
	 * @param opp the opp
	 * @return the string
	 */
	public static String connectedTrueMatchUpTrue(String opp) {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "true");
		map.put("matchUp", "true");
		map.put("side", opp);

		return new Gson().toJson(map);
	}

	/**
	 * Card added string.
	 *
	 * @return the string
	 */
	public static String cardAdded() {
		Map<String, String> map = new HashMap<>();

		map.put("card", "added");

		return new Gson().toJson(map);
	}

	/**
	 * Convert to json string.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public static String convertToJson(Object obj) {
		return new Gson().toJson(obj);
	}

	/**
	 * Convert json to card played card.
	 *
	 * @param json the json
	 * @return the played card
	 */
	public static PlayedCard convertJsonToPlayedCard(String json) {
		return new Gson().fromJson(json, PlayedCard.class);
	}
}
