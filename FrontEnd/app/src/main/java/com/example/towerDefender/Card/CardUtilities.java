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
    public static GameObjectSprite getGameObjectSpriteLeftFacing(Context context, Card card, int x, int y){
        GameObjectSprite toReturn;
        if(card.cardName.contains("Wizard") || card.cardName.contains("wizard")){
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper2), x, y);
        } else if(card.cardName.contains("Reaper") || card.cardName.contains("reaper")){
            toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper), x, y);
        } else if(card.cardName.contains("tower")){
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.friendly_tower), x, y);
        } else if(card.cardName.contains("TEST-CARD-DO-NOT-USE")) {// for testing purposes, so that tests do not need to rely upon the application resources
            return null;
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
    public static GameObjectSprite getGameObjectSpriteRightFacing(Context context, Card card, int x, int y){
        GameObjectSprite toReturn;
        if(card.cardName.contains("Wizard") || card.cardName.contains("wizard")){
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper2), x, y, false);
        } else if(card.cardName.contains("Reaper") || card.cardName.contains("reaper")){
            toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper), x, y, false);
        } else if(card.cardName.contains("tower")){
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_tower), x, y, false);
        } else if(card.cardName.contains("TEST-CARD-DO-NOT-USE")) { // for testing purposes, so that tests do not need to rely upon the application resources
            return null;
        } else {
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.flame_demon), x, y, false);
        }
        toReturn.image = Bitmap.createScaledBitmap(toReturn.image, normalizedInventorySize, normalizedInventorySize, false);
        toReturn.xEnd = toReturn.xStart + normalizedInventorySize;
        toReturn.yEnd = toReturn.yStart + normalizedInventorySize;
        return toReturn;
    }

}
