package shared;

import com.google.gson.Gson;

/**
 * The type Played card.
 */
public class PlayedCard {
    private String name;
    private String description;
    private int cost;
    private int damage;
    // private int currentDamage; // Can be used when spells are implemented.
    private int hitPoints;
    private int speed;
    // private int currentSpeed; // If they are attacking this would be 0.
    // Can be used when spells are implemented.
    private String type;
    private int range;
    private int xValue;
    private int yValue;
    private String player;
    // Possible variables for future.
    private boolean attacking;
    // private String cardAttacking; // The card this card is attacking.
    // private boolean healing;

    /**
     * Instantiates a new Played card.
     *
     * @param name        the name
     * @param description the description
     * @param cost        the cost
     * @param damage      the damage
     * @param hitPoints   the hit points
     * @param speed       the speed
     * @param type        the type
     * @param range       the range
     * @param xValue      the x value
     * @param yValue      the y value
     * @param player      the player
     */
    public PlayedCard(String name, String description, int cost, int damage, int hitPoints, int speed, String type, int range, int xValue, int yValue, String player) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.damage = damage;
        this.hitPoints = hitPoints;
        this.speed = speed;
        this.type = type;
        this.range = range;
        this.xValue = xValue;
        this.yValue = yValue;
        this.player = player;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets damage.
     *
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets damage.
     *
     * @param damage the damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Sets hit points.
     *
     * @param hitPoints the hit points
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets speed.
     *
     * @param speed the speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets range.
     *
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets range.
     *
     * @param range the range
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getxValue() {
        return xValue;
    }

    /**
     * Sets value.
     *
     * @param xValue the x value
     */
    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getyValue() {
        return yValue;
    }

    /**
     * Sets value.
     *
     * @param yValue the y value
     */
    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(String player) {
        this.player = player;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
