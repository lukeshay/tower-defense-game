package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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


}
