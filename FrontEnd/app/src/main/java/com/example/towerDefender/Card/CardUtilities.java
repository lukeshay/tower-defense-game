package com.example.towerDefender.Card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.towerDefender.Game.GameObjectSprite;
import com.example.towerDefender.Game.Sprite;
import com.example.towerDefender.R;

import static com.example.towerDefender.Game.Sprite.normalizedInventorySize;

public class CardUtilities {

    public static GameObjectSprite getGameObjectSprite(Context context, Card card, int x, int y, boolean leftFacing){
        GameObjectSprite toReturn;
        if(card.cardName.contains("Wizard") || card.cardName.contains("wizard")){
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper2), x, y, leftFacing);
        } else if(card.cardName.contains("Reaper") || card.cardName.contains("reaper")){
            toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.reaper), x, y, leftFacing, 3, 64, 64);
            //toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.skeleton_walk), x, y, leftFacing, 5, 128, 64);
            //toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.reaper), x, y, false);
        } else if(card.cardName.contains("Skeleton")){
            toReturn= new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.skeleton_walk), x, y, leftFacing, 18, 16, 33);
        } else if(card.cardName.contains("tower")){
            if(leftFacing){
                toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.friendly_tower), x, y, leftFacing);
            } else {
                toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_tower), x, y, leftFacing);
            }

        } else if(card.cardName.contains("TEST-CARD-DO-NOT-USE")) { // for testing purposes, so that tests do not need to rely upon the application resources
            return null;
        } else {
            toReturn = new GameObjectSprite(BitmapFactory.decodeResource(context.getResources(), com.example.towerDefender.R.drawable.flame_demon), x, y, leftFacing, 3, 63, 64);
        }
        toReturn.image = Bitmap.createScaledBitmap(toReturn.image, normalizedInventorySize, normalizedInventorySize, false);
        toReturn.xEnd = toReturn.xStart + normalizedInventorySize;
        toReturn.yEnd = toReturn.yStart + normalizedInventorySize;
        return toReturn;
    }

}
