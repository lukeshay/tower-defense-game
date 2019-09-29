package com.pvptowerdefense.server.cards.models;

public enum Type {
	UNIT("UNIT"),
	SPELL("SPELL");

	private String type;

	Type(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public boolean typeEquals(String type) {
		return this.type.equals(type);
	}
}
