package com.pvptowerdefense.server.socket.models;

import java.util.HashMap;
import java.util.Map;

public class Messages {
	public static Map<String, Boolean> connectedTrueMatchupFalse() {
		Map<String, Boolean> map = new HashMap<>();

		map.put("connected", true);
		map.put("matchup", false);

		return map;
	}

	public static Map<String, Boolean> connectedTrueMatchupTrue() {
		Map<String, Boolean> map = new HashMap<>();

		map.put("connected", true);
		map.put("matchup", true);

		return map;
	}

	public static Map<String, Boolean> connectedFalseMatchupFalse() {
		Map<String, Boolean> map = new HashMap<>();

		map.put("connected", false);
		map.put("matchup", false);

		return map;
	}
}
