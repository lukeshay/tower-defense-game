package com.example.towerDefender.Card;
import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class Card {
    public enum CardType{
        UNIT, SPELL
    }

    private transient CardType cardType;

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
        this.setCardType(type);
    }


    public CardType getCardType(){
        return cardType;
    }

    /**
     * Sets this {@link Card}'s {@link Card.CardType} to the provided {@link Card.CardType}.
     */
    public void setCardType(CardType type){
        this.cardType = type;
    }

    /**
     * Sets this {@link Card}'s {@link Card.CardType} based upon the given {@link String}.
     * @param type a {@link String}, either "UNIT" or "SPELL", corresponding to the type to set the card type to
     */
    public void setCardType(String type){
        switch(type){
            case("SPELL"):
                this.cardType = CardType.SPELL;
                break;
            case("UNIT"):
            default:
                this.cardType = CardType.UNIT;
        }
    }


}
