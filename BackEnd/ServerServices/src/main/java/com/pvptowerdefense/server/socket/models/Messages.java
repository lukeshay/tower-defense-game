package com.pvptowerdefense.server.socket.models;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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


	public static byte[] serialize(Object obj) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			return out.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}

	public static ByteBuffer serializeToByteBuffer(Object obj) {
		return ByteBuffer.wrap(Objects.requireNonNull(serialize(obj)));
	}

	public static Object deserialize(byte[] data) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			return is.readObject();
		} catch (Exception e) {
			return null;
		}
	}
}
