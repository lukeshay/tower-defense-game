package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class JsonToObject {

    public static Card convertCard(String json){
        Gson test = new Gson();
        return test.fromJson(json, Card.class);
    }

    public static Collection<Card> convertCardArray(String json){
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Card>>(){}.getType();
        Collection<Card> enums = gson.fromJson(json, collectionType);
        return enums;
    }


}
