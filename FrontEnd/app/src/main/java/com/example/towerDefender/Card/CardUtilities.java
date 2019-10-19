package com.example.towerDefender.Card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.towerDefender.Game.GameObjectSprite;
import com.example.towerDefender.Game.Sprite;

import static com.example.towerDefender.Game.Sprite.normalizedInventorySize;

public class CardUtilities {

    /**
     * @param context the game context, used for getting resources
     * @param card the card to construct the gameObjectSprite from
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @return a {@link GameObjectSprite} based upon the object represented by the {@link Card} provided
     */
    public static GameObjectSprite getGameObjectSpriteForCard(Context context, Card card, int x, int y){
        GameObjectSprite toReturn;
        switch(card.cardName){
            case "Card 1":
                toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper), x, y);
                break;
            case "Card 2":
                toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper2), x, y);
                break;
            default:
                toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.flame_demon), x, y);
        }
        toReturn.image = Bitmap.createScaledBitmap(toReturn.image, normalizedInventorySize, normalizedInventorySize, false);
        toReturn.xEnd = toReturn.xStart + normalizedInventorySize;
        toReturn.yEnd = toReturn.yStart + normalizedInventorySize;
        return toReturn;
    }
}
