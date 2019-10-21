package com.example.towerDefender.Card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.towerDefender.Game.GameObjectSprite;
import com.example.towerDefender.Game.Sprite;
import com.example.towerDefender.R;

import static com.example.towerDefender.Game.Sprite.normalizedInventorySize;

public class CardUtilities {

    /**
     * Returns a sprite representing the provided card at the provided location.
     * @param context the game context, used for getting resources
     * @param card the card to construct the gameObjectSprite from
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @return a {@link GameObjectSprite} based upon the object represented by the {@link Card} provided
     */
    public static GameObjectSprite getGameObjectSpriteForCard(Context context, Card card, int x, int y){
        GameObjectSprite toReturn;
        if(card.cardName.contains("Card 1") || card.cardName.contains("Reaper 2")){
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper2), x, y);
        } else if(card.cardName.contains("Card 2") || card.cardName.contains("Reaper")){
            toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper), x, y);
        } else if(card.cardName.contains("Tower")){
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.friendly_tower), x, y);
        } else {
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.flame_demon), x, y);
        }
        toReturn.image = Bitmap.createScaledBitmap(toReturn.image, normalizedInventorySize, normalizedInventorySize, false);
        toReturn.xEnd = toReturn.xStart + normalizedInventorySize;
        toReturn.yEnd = toReturn.yStart + normalizedInventorySize;
        return toReturn;
    }

    /**
     * Returns a flipped version of the expected sprite for a given card. Used for cards played by opponent
     * @param context the game context, used for getting resources
     * @param card the card to construct the gameObjectSprite from
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @return a {@link GameObjectSprite} based upon the object represented by the {@link Card} provided
     */
    public static GameObjectSprite getEnemySprite(Context context, Card card, int x, int y){
        GameObjectSprite toReturn;
        if(card.cardName.contains("Card 1") || card.cardName.contains("Reaper 2")){
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper2), x, y);
        } else if(card.cardName.contains("Card 2") || card.cardName.contains("Reaper")){
            toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper), x, y);
        } else if(card.cardName.contains("Tower")){
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_tower), x, y);
        } else {
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.flame_demon), x, y);
        }
        toReturn.image = Bitmap.createScaledBitmap(toReturn.image, normalizedInventorySize, normalizedInventorySize, false);
        toReturn.xEnd = toReturn.xStart + normalizedInventorySize;
        toReturn.yEnd = toReturn.yStart + normalizedInventorySize;
        return toReturn;
    }

}
