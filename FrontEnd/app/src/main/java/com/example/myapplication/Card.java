package com.example.myapplication;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.google.gson.annotations.SerializedName;

public class Card {
    public enum CardType{
        UNIT, SPELL
    }

    public CardType cardType;
    public Sprite sprite;

    @SerializedName("name")
    public String cardName;
    @SerializedName("description")
    public String cardDescription;
    @SerializedName("cost")
    public int castingCost;
    @SerializedName("damage")
    public int damage;
    @SerializedName("hitPoints")
    public int hitPoints;
    @SerializedName("speed")
    public int speed;
    //@SerializedName("type")
    public int range;

    public Card(CardType cardType, Bitmap image){
        //TODO: not always a Character
        sprite = new Character(image,0,0);
        this.cardType = cardType;
        sprite.image = image;
    }

    public Card(String name, String cardDescription, int castingCost, int damage, int hitPoints, int speed, int range, String bitmapName){
        this.cardName = name;
        this.cardDescription = cardDescription;
        this.castingCost = castingCost;
        this.damage = damage;
        this.hitPoints = hitPoints;
        this.speed = speed;
        this.range = range;
        //int bitmapId = Resources.getSystem().getIdentifier(bitmapName, "drawable", "android" );
        //sprite = new Character(BitmapFactory.decodeResource(Resources.getSystem(), bitmapId), 0, 0);
    }

    public void draw(Canvas canvas){
        sprite.draw(canvas);
    }

}
