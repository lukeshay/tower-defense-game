package com.pvptowerdefense.server.spring.cards;

/**
 * The enum Card type.
 */
public enum CardType {
	/**
	 * Unit card type.
	 */
	UNIT("UNIT"),
	/**
	 * Spell card type.
	 */
	SPELL("SPELL");

	private String type;

	CardType(String type) {
		this.type = type;
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
	 * Type equals boolean.
	 *
	 * @param type the type
	 * @return the boolean
	 */
	public boolean typeEquals(String type) {
		return this.type.equals(type);
	}
}
