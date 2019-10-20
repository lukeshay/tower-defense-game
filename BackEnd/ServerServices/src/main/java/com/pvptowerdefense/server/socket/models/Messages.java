package com.pvptowerdefense.server.socket.models;

import shared.PlayedCard;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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

//	public static ByteBuffer serializeListToByteBuffer(List<PlayedCard> list) throws IOException {
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		ObjectOutputStream out = new ObjectOutputStream(baos);
//		for (PlayedCard element : list) {
//			out.writeObject(element);
//		}
//		return ByteBuffer.wrap(baos.toByteArray());
//	}

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

	private static byte[] serialize(Object obj) throws IOException {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ByteBuffer serializeToByteBuffer(Object obj) throws IOException {
		return ByteBuffer.wrap(Objects.requireNonNull(serialize(obj)));
	}

	public static String convertToJson(List<PlayedCard> list) {
		StringBuilder sb = new StringBuilder();
		AtomicBoolean added = new AtomicBoolean(false);

		sb.append("{");

		list.forEach(e -> {
			if (added.get()) {
				sb.append(", ");
			}
			sb.append(e.toString());
			added.set(true);
		});

		sb.append("}");

		return sb.toString();
	}
}
