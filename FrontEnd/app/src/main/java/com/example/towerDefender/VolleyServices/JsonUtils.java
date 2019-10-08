package com.example.towerDefender.VolleyServices;

import com.example.towerDefender.Card.Card;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Type;
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

    public static void main(String[] args){
        String json = "[{\"name\":\"Card 1\",\"description\":\"Card 1 desc\",\"cost\":1,\"damage\":1,\"hitPoints\":1,\"speed\":1,\"type\":\"UNIT\",\"range\":50},{\"name\":\"Card 2\",\"description\":\"Card 2 desc\",\"cost\":2,\"damage\":2,\"hitPoints\":2,\"speed\":2,\"type\":\"SPELL\",\"range\":40},{\"name\":\"Card 3\",\"description\":\"Card 3 desc\",\"cost\":3,\"damage\":3,\"hitPoints\":3,\"speed\":3,\"type\":\"UNIT\",\"range\":5},{\"name\":\"Card 4\",\"description\":\"Card 4 desc\",\"cost\":4,\"damage\":4,\"hitPoints\":4,\"speed\":4,\"type\":\"SPELL\",\"range\":100},{\"name\":\"added3 card\",\"description\":\"added card description\",\"cost\":1,\"damage\":1,\"hitPoints\":1,\"speed\":1,\"type\":\"UNIT\",\"range\":50},{\"name\":\"added4 card\",\"description\":\"added card description\",\"cost\":1,\"damage\":1,\"hitPoints\":1,\"speed\":1,\"type\":\"UNIT\",\"range\":0},{\"name\":\"added1 card\",\"description\":\"added card description\",\"cost\":1,\"damage\":1,\"hitPoints\":1,\"speed\":1,\"type\":\"UNIT\",\"range\":50},{\"name\":\"added card\",\"description\":\"added card description\",\"cost\":1,\"damage\":1,\"hitPoints\":1,\"speed\":1,\"type\":\"UNIT\",\"range\":0},{\"name\":\"addasdfed1 card\",\"description\":\"added card description\",\"cost\":1,\"damage\":1,\"hitPoints\":1,\"speed\":0,\"type\":\"UNIT\",\"range\":50},{\"name\":\"hello from front end\",\"description\":\"testing sending cards to DB from app\",\"cost\":5,\"damage\":5,\"hitPoints\":5,\"speed\":2,\"type\":\"UNIT\",\"range\":40}]";
        Collection<Card> cards = jsonToCardArray(json);
        System.out.println("");
    }
}
