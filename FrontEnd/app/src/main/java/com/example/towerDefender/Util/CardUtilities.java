package com.example.towerDefender.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.Deck;
import com.example.towerDefender.Card.OwnedDeck;
import com.example.towerDefender.Game.GameObjectSprite;
import com.example.towerDefender.Game.Player;
import com.example.towerDefender.Game.SpriteAnimation;
import com.example.towerDefender.R;
import com.example.towerDefender.VolleyServices.CardRestServices;
import com.example.towerDefender.VolleyServices.UserRestServices;

import java.util.ArrayList;

import static com.example.towerDefender.Game.Sprite.normalizedInventorySize;

public class CardUtilities {

    public static GameObjectSprite getGameObjectSprite(Context context, Card card, int x, int y, boolean leftFacing){
        GameObjectSprite toReturn;
        if(card.cardName.contains("Wizard") || card.cardName.contains("wizard")){
            toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.wizard_idle), x, y, leftFacing, 10);
            toReturn.setAttackAnimation(new SpriteAnimation(BitmapFactory.decodeResource(context.getResources(), R.drawable.wizard_attack),9));
        } else if(card.cardName.contains("Minotaur")){
            toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.minotaur_walk), x, y, leftFacing, 7);
            toReturn.setAttackAnimation(new SpriteAnimation(BitmapFactory.decodeResource(context.getResources(), R.drawable.minotaur_attack), 8));
        } else if(card.cardName.contains("Blob")){
            toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.blob_walk), x, y, leftFacing, 8);
            toReturn.setAttackAnimation(new SpriteAnimation(BitmapFactory.decodeResource(context.getResources(), R.drawable.blob_attack), 8));
        } else if(card.cardName.contains("tower")){
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.friendly_tower), x, y, leftFacing);
        } else if(card.cardName.contains("TEST-CARD-DO-NOT-USE")) { // for testing purposes, so that tests do not need to rely upon the application resources
            return null;
        } else {
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.flame_demon), x, y, leftFacing, 3);
        }
        toReturn.image = Bitmap.createScaledBitmap(toReturn.image, normalizedInventorySize, normalizedInventorySize, false);
        toReturn.xEnd = toReturn.xStart + normalizedInventorySize;
        toReturn.yEnd = toReturn.yStart + normalizedInventorySize;
        return toReturn;
    }

    public static OwnedDeck getTestDeck(){
        String deckJson = "{\n" +
                "        \"deck\": [\n" +
                "            {\n" +
                "        \"name\": \"Fire Golem\",\n" +
                "        \"description\": \"Medium fire golem. Medium damage, high HP, medium range\",\n" +
                "        \"cost\": 2,\n" +
                "        \"damage\": 2,\n" +
                "        \"hitPoints\": 4,\n" +
                "        \"speed\": 2,\n" +
                "        \"type\": \"UNIT\",\n" +
                "        \"range\": 250\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Golem King\",\n" +
                "        \"description\": \"Strong golem. Medium damage, medium range, high hp\",\n" +
                "        \"cost\": 2,\n" +
                "        \"damage\": 2,\n" +
                "        \"hitPoints\": 15,\n" +
                "        \"speed\": 2,\n" +
                "        \"type\": \"UNIT\",\n" +
                "        \"range\": 250\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Wizard\",\n" +
                "        \"description\": \"High range, medium damage, low HP\",\n" +
                "        \"cost\": 2,\n" +
                "        \"damage\": 2,\n" +
                "        \"hitPoints\": 2,\n" +
                "        \"speed\": 3,\n" +
                "        \"type\": \"UNIT\",\n" +
                "        \"range\": 650\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Master wizard\",\n" +
                "        \"description\": \"Strong wizard\",\n" +
                "        \"cost\": 5,\n" +
                "        \"damage\": 3,\n" +
                "        \"hitPoints\": 5,\n" +
                "        \"speed\": 3,\n" +
                "        \"type\": \"UNIT\",\n" +
                "        \"range\": 650\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Blob\",\n" +
                "        \"description\": \"Weak ground troop.\",\n" +
                "        \"cost\": 1,\n" +
                "        \"damage\": 2,\n" +
                "        \"hitPoints\": 5,\n" +
                "        \"speed\": 2,\n" +
                "        \"type\": \"UNIT\",\n" +
                "        \"range\": 250\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Lesser Minotaur\",\n" +
                "        \"description\": \"Basic minotaur. High damage, low range\",\n" +
                "        \"cost\": 1,\n" +
                "        \"damage\": 3,\n" +
                "        \"hitPoints\": 5,\n" +
                "        \"speed\": 2,\n" +
                "        \"type\": \"UNIT\",\n" +
                "        \"range\": 250\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Minotaur\",\n" +
                "        \"description\": \"Medium minotaur. High damage, medium range\",\n" +
                "        \"cost\": 5,\n" +
                "        \"damage\": 5,\n" +
                "        \"hitPoints\": 6,\n" +
                "        \"speed\": 3,\n" +
                "        \"type\": \"UNIT\",\n" +
                "        \"range\": 250\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Minotaur king\",\n" +
                "        \"description\": \"Strong minotaur. High damage, high HP, medium range\",\n" +
                "        \"cost\": 5,\n" +
                "        \"damage\": 5,\n" +
                "        \"hitPoints\": 7,\n" +
                "        \"speed\": 3,\n" +
                "        \"type\": \"UNIT\",\n" +
                "        \"range\": 250\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Lesser Fire Golem\",\n" +
                "        \"description\": \"Weak fire golem. Medium damage, HP, and range\",\n" +
                "        \"cost\": 1,\n" +
                "        \"damage\": 2,\n" +
                "        \"hitPoints\": 2,\n" +
                "        \"speed\": 2,\n" +
                "        \"type\": \"UNIT\",\n" +
                "        \"range\": 250\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Lesser Wizard\",\n" +
                "        \"description\": \"High range, medium damage, low HP\",\n" +
                "        \"cost\": 1,\n" +
                "        \"damage\": 2,\n" +
                "        \"hitPoints\": 1,\n" +
                "        \"speed\": 3,\n" +
                "        \"type\": \"UNIT\",\n" +
                "        \"range\": 650\n" +
                "    },\n" +
                "        ],\n" +
                "        \"userId\": \"test1\",\n" +
                "        \"deckId\": 15,\n" +
                "        \"deckName\": \"deck1\"\n" +
                "    }";
        return JsonUtility.jsonToOwnedDecks(deckJson);
    }
}
