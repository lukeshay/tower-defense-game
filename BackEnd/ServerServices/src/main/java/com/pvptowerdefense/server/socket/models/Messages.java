package com.pvptowerdefense.server.socket.models;

import shared.PlayedCard;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.Map;

public class Messages {
	public static Map<String, String> connectedTrueMatchUpFalse() {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "true");
		map.put("matchUp", "false");
		map.put("opp", "");

		return map;
	}

	public static Map<String, String> connectedTrueMatchUpTrue(String opp) {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "true");
		map.put("matchUp", "true");
		map.put("opp", opp);

		return map;
	}

	public static Map<String, String> connectedFalseMatchUpFalse() {
		Map<String, String> map = new HashMap<>();

		map.put("connected", "false");
		map.put("matchUp", "false");
		map.put("opp", "");

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

	public static ByteBuffer serializeListToByteBuffer(List<PlayedCard> list) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(baos);
		for (PlayedCard element : list) {
			out.writeObject(element);
		}
		return ByteBuffer.wrap(baos.toByteArray());
	}

	public static Object deserialize(byte[] data) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			return is.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
