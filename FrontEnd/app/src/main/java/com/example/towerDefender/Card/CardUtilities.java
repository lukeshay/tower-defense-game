package com.example.towerDefender.Card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.towerDefender.Game.GameObjectSprite;
import com.example.towerDefender.Game.SpriteAnimation;
import com.example.towerDefender.R;

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

}
