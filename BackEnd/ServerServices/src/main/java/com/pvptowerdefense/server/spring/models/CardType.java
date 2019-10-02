package com.pvptowerdefense.server.spring.models;

public enum CardType {
	UNIT("UNIT"),
	SPELL("SPELL");

	private String type;

	CardType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public boolean typeEquals(String type) {
		return this.type.equals(type);
	}
}
