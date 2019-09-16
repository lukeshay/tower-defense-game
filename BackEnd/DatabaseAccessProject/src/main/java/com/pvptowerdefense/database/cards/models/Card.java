package com.pvptowerdefense.database.cards.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Cards")
public class Card {
    @Id
    private String name;

    @NotEmpty
    private int damage;

    @NotEmpty
    private int hitpoints;

    @NotEmpty
    private int speed;

    @NotEmpty
    private int cost;

    @NotEmpty
    private boolean melee;

    private Card() {}

    public Card(String name, int damage, int hitpoints, int speed, int cost,
                boolean melee) {
        assertValidParams(name, damage, hitpoints, speed, cost);
        this.name = name;
        this.damage = damage;
        this.hitpoints = hitpoints;
        this.speed = speed;
        this.cost = cost;
        this.melee = melee;
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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
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

    public boolean isMelee() {
        return melee;
    }

    public void setMelee(boolean melee) {
        this.melee = melee;
    }
}
