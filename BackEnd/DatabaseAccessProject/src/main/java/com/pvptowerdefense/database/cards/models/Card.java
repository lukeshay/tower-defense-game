package com.pvptowerdefense.database.cards.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Card {
    @Id
    @Column(unique=true)
    private String name;
    private int ad;
    private int hp;
    private int speed;
    private int cost;

    private Card() {}

    public Card(String name, int ad, int hp, int speed, int cost) {
        assertValidParams(name, ad, hp, speed, cost);
        this.name = name;
        this.ad = ad;
        this.hp = hp;
        this.speed = speed;
        this.cost = cost;
    }

    /**
     * Validates the params. Below you can see the regulations.
     * @param name This must not be null
     * @param ad Must be greater than or equal to 0
     * @param hp Must be greater than 0
     * @param speed Must be greater than or equal to 0
     * @param cost No rules
     */
    private static void assertValidParams(String name, int ad, int hp,
                                          int speed, int cost) {
        assert name != null;
        assert speed >= 0;
        assert ad >= 0;
        assert hp > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAd() {
        return ad;
    }

    public void setAd(int ad) {
        this.ad = ad;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
