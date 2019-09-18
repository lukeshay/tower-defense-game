package com.example.myapplication;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Card {
    public enum CardType{
        UNIT, SPELL
    }
    public CardType cardType;
    public Sprite sprite;

    public Card(CardType cardType, Bitmap image){
        //TODO: not always a Character
        sprite = new Character(image,0,0);
        this.cardType = cardType;
        sprite.image = image;
    }

    public void draw(Canvas canvas){
        sprite.draw(canvas);
    }

}
