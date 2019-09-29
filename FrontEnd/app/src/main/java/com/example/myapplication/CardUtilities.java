package com.example.myapplication;

import android.content.Context;
import android.graphics.BitmapFactory;

public class CardUtilities {

    public static Sprite getBitmapForCard(Context context, Card card){
        //TODO: not always characters
        Sprite toReturn;
        switch(card.cardName){
            case "Card 1":
                toReturn = new Character(BitmapFactory.decodeResource(context.getResources(), R.drawable.reaper), 0, 0);
                break;
            case "Card 2":
                toReturn= new Character(BitmapFactory.decodeResource(context.getResources(), R.drawable.reaper2), 0, 0);
                break;
            default:
                toReturn = new Character(BitmapFactory.decodeResource(context.getResources(), R.drawable.flame_demon), 0, 0);
        }
        return toReturn;
    }
}
