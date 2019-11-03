package com.pvptowerdefense.server.socket.models;

import com.google.gson.Gson;

import java.util.*;
import java.util.Map;

public class Messages {
	public static String connectedTrueMatchUpFalse() {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "true");
		map.put("matchUp", "false");
		map.put("side", "");

		return new Gson().toJson(map);
	}

	public static String connectedTrueMatchUpTrue(String opp) {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "true");
		map.put("matchUp", "true");
		map.put("side", opp);

		return new Gson().toJson(map);
	}

	public static String connectedFalseMatchUpFalse() {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "false");
		map.put("matchUp", "false");
		map.put("side", "");

		return new Gson().toJson(map);
	}

	public static String gameWin() {
		Map<String, String> map = new HashMap<>();

		map.put("game", "win");

		return new Gson().toJson(map);
	}

	public static String gameLoss() {
		Map<String, String> map = new HashMap<>();

		map.put("game", "loss");

		return new Gson().toJson(map);
	}

	public static String cardAdded() {
		Map<String, String> map = new HashMap<>();

		map.put("card", "added");

		return new Gson().toJson(map);
	}

	public static String convertToJson(Object obj) {
		return new Gson().toJson(obj);
	}

	public static PlayedCard convertJsonToCard(String json) {
		return new Gson().fromJson(json, PlayedCard.class);
	}

	public static String cardNotAdded(String reason) {
		Map<String, String> map = new HashMap<>();

		map.put("card", "not added");
		map.put("reason", reason);

		return new Gson().toJson(map);
	}
}
