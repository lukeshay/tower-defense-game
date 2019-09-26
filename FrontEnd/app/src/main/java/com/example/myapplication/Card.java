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

    private transient CardType cardType;
    private transient Sprite sprite;

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
    @SerializedName("type")
    public String type;
    @SerializedName("range")
    public int range;

    //this constructor relies too heavily on dummy values. It should only be used for testing sendCardToDB
    @Deprecated
    public Card(CardType cardType, Bitmap image){
        //TODO: not always a Character
        sprite = new Character(image,0,0);
        this.cardType = cardType;
        this.cardDescription = "testing sending cards to DB from app";
        cardName = "name";
        castingCost = 5;
        damage = 5;
        hitPoints = 5;
        speed = 2;
        type = "UNIT";
        range = 40;

    }

    public Card(String name, String cardDescription, int castingCost, int damage, int hitPoints, int speed, String type, int range){
        this.cardName = name;
        this.cardDescription = cardDescription;
        this.castingCost = castingCost;
        this.damage = damage;
        this.hitPoints = hitPoints;
        this.speed = speed;
        this.type = type;
        this.range = range;
        sprite = new Character(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.flame_demon), 0,0);
        //int bitmapId = Resources.getSystem().getIdentifier(bitmapName, "drawable", "android" );
        //sprite.image = BitmapFactory.decodeResource(Resources.getSystem(), bitmapId);
    }

    public void draw(Canvas canvas){
        sprite.draw(canvas);
    }

    public Sprite getSprite(){
        return CardUtilities.getBitmapForCard(this);
    }

    public CardType getCardType(){
        return cardType;
    }


}
