package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class JsonUtils {

    public static Card jsonToCard(String json){
        Gson test = new Gson();
        return test.fromJson(json, Card.class);
    }

    public static Collection<Card> jsonToCardArray(String json){
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Card>>(){}.getType();
        Collection<Card> enums = gson.fromJson(json, collectionType);
        return enums;
    }

    public static JSONObject cardtoJson(Card card){
        Gson gson = new Gson();
        String json = gson.toJson(card);
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject;
        } catch(Exception e){
            //TODO: handle parse exception
            System.out.println("Encountered a parse exception: " + e.getMessage());
            return null;
        }
    }

}
