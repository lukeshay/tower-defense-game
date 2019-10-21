package com.example.towerDefender.SocketServices;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.towerDefender.Card.PlayedCard;

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

    public static ByteBuffer serializeToByteBuffer(Object obj) throws IOException {
        return ByteBuffer.wrap(Objects.requireNonNull(serialize(obj)));
    }

}
