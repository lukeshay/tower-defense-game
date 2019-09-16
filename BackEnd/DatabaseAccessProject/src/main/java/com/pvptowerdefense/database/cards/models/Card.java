package com.pvptowerdefense.database.cards.models;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Cards")
public class Card {
    @Id
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "COST", nullable = false)
    private int cost;

    @Column(name = "DAMAGE", nullable = false)
    private int damage;

    @Column(name = "HIT_POINTS", nullable = false)
    private int hitPoints;

    @Column(name = "SPEED", nullable = false)
    private int speed;

    @Column(name = "MELEE", nullable = false)
    private boolean melee;

    /**
     * Default constructor. Used when calling other services.
     */
    public Card() {
    }

    /**
     * Constructor for a card.
     *
     * @param name        Name of the card.
     * @param description Description of the card.
     * @param cost        Cost of the card.
     * @param damage      Damage of the card.
     * @param hitPoints   Hit points of the card.
     * @param speed       Speed of the card.
     * @param melee       Whether or not the card is melee.
     */
    public Card(String name, String description, int cost, int damage,
                int hitPoints, int speed, boolean melee) {

        this.name = name;
        this.description = description;
        this.cost = cost;
        this.damage = damage;
        this.hitPoints = hitPoints;
        this.speed = speed;
        this.melee = melee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isMelee() {
        return melee;
    }

    public void setMelee(boolean melee) {
        this.melee = melee;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("name", this.getName())
                .append("description", this.getDescription())
                .append("cost", this.getCost())
                .append("damage", this.getDamage())
                .append("hit points", this.getHitPoints())
                .append("speed", this.getSpeed())
                .append("melee", this.isMelee())
                .toString();
    }
}
