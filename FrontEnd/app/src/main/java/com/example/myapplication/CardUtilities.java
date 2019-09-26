package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class CardUtilities {

    public static Sprite getBitmapForCard(Card card){
        //TODO: not always characters
        Sprite toReturn = new Character(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.reaper), 0, 0);
        switch(card.cardName){
            case "Card 1":
                toReturn.image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.flame_demon);
                break;
            case "Card 2":
                toReturn.image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.reaper);
                break;
            default:
                toReturn.image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.reaper2);
            }
        return toReturn;
    }
}
