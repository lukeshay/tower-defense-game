package com.pvptowerdefense.server.socket.models;

import com.google.gson.Gson;
import shared.PlayedCard;

import java.util.*;
import java.util.Map;

public class Messages {
	public static Map<String, String> connectedTrueMatchUpFalse() {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "true");
		map.put("matchUp", "false");
		map.put("side", "");

		return map;
	}

	public static Map<String, String> connectedTrueMatchUpTrue(String opp) {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "true");
		map.put("matchUp", "true");
		map.put("side", opp);

		return map;
	}

	public static Map<String, String> connectedFalseMatchUpFalse() {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "false");
		map.put("matchUp", "false");
		map.put("side", "");

		return map;
	}

	public static Map<String, String> gameWin() {
		Map<String, String> map = new HashMap<>();

		map.put("game", "win");

		return map;
	}

	public static Map<String, String> gameLoss() {
		Map<String, String> map = new HashMap<>();

		map.put("game", "loss");

		return map;
	}

	public static Map<String, String> cardAdded() {
		Map<String, String> map = new HashMap<>();

		map.put("card", "added");

		return map;
	}

	public static String convertToJson(Object obj) {
		return new Gson().toJson(obj);
	}

	public static PlayedCard convertJsonToCard(String json) {
		return new Gson().fromJson(json, PlayedCard.class);
	}
}
