package com.pvptowerdefense.test.websocketclient;

import shared.PlayedCard;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;

public class Message {
	public static byte[] serialize(Object obj) throws IOException {
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

	public static Object deserialize(byte[] data) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			return is.readObject();
		} catch (Exception e) {
			return null;
		}
	}

	public static List<PlayedCard> deserializeToList(byte[] data) throws IOException, ClassNotFoundException {
		ObjectInputStream ois =
				new ObjectInputStream(new ByteArrayInputStream(data));
		return (ArrayList<PlayedCard>) ois.readObject();
	}

	public static String convertToJson(Object message) {
		Gson g = new Gson();

		return g.toJson(message);
	}
}
