package com.pvptowerdefense.test.websocketclient;

public class PlayedCard extends Card {
    private int xValue;
    private int yValue;
    private String player;


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
    public PlayedCard(String name, String description, int cost, int damage, int hitPoints, int speed, String type, int range, int xValue, int yValue, String player) {
        super(name, description, cost, damage, hitPoints, speed, type, range);
        this.xValue = xValue;
        this.yValue = yValue;
        this.player = player;
    }


    public int getXValue() {
        return xValue;
    }

    public void setXValue(int xValue) {
        this.xValue = xValue;
    }

    public int getYValue() {
        return yValue;
    }

    public void setYValue(int yValue) {
        this.yValue = yValue;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
