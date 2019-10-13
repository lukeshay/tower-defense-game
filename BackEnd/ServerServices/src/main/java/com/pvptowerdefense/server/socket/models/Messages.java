package com.pvptowerdefense.server.socket.models;

import java.util.HashMap;
import java.util.Map;

public class Messages {
	public static Map<String, Boolean> connectedTrueMatchUpFalse() {
		Map<String, Boolean> map = new HashMap<>();

		map.put("connected", true);
		map.put("matchup", false);

		return map;
	}

	public static Map<String, Boolean> connectedTrueMatchUpTrue() {
		Map<String, Boolean> map = new HashMap<>();

		map.put("connected", true);
		map.put("matchup", true);

		return map;
	}

	public static Map<String, Boolean> connectedFalseMatchUpFalse() {
		Map<String, Boolean> map = new HashMap<>();

		map.put("connected", false);
		map.put("matchup", false);

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
}
