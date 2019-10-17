package com.pvptowerdefense.test.websocketclient;

/**
 * The type Card.
 */
public class Card {

	private String name;
	private String description;
	private int cost;
	private int damage;
	private int hitPoints;
	private int speed;
	private String type;
	private int range;

	/**
	 * Instantiates a new Card.
	 */
	private Card() {
	}

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
	public Card(String name, String description, int cost, int damage,
	            int hitPoints, int speed, String type, int range) {
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.damage = damage;
		this.hitPoints = hitPoints;
		this.speed = speed;
		setType(type);
		this.range = range;
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
	 * TODO - verify it matches enum with following fields: unit, spell
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
}
