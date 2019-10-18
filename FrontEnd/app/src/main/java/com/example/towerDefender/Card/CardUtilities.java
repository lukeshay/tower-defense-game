package com.example.towerDefender.Card;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.towerDefender.Game.Character;
import com.example.towerDefender.Game.Sprite;

public class CardUtilities {

    public static Sprite getBitmapForCard(Context context, Card card){
        //TODO: not always characters
        Sprite toReturn;
        switch(card.cardName){
            case "Card 1":
                toReturn = new Character(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper), 0, 0);
                break;
            case "Card 2":
                toReturn= new Character(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper2), 0, 0);
                break;
            default:
                toReturn = new Character(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.flame_demon), 0, 0);
        }
        return toReturn;
    }
}