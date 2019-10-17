package com.pvptowerdefense.server.socket.models;

import com.pvptowerdefense.server.spring.models.Card;

public class PlayedCard extends Card {
    private int xValue;
    private int yValue;
    private String userId;


    /**
     * Instantiates a new Card.
     *
     * @param name        the name
     * @param description the description
     * @param cost        the cost
     * @param damage      the damage
     * @param hitPoints   the hit points
     * @param speed       the speed
     * @param type        the type
     * @param range       the range
     */
    public PlayedCard(String name, String description, int cost, int damage, int hitPoints, int speed, String type, int range, int xValue, int yValue, String userId) {
        super(name, description, cost, damage, hitPoints, speed, type, range);
        this.xValue = xValue;
        this.yValue = yValue;
        this.userId = userId;
    }


    public int getxValue() {
        return xValue;
    }

    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    public int getyValue() {
        return yValue;
    }

    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
