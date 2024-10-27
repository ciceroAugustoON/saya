package com.backend.saya.entities.enumeration;

public enum WeekDay {
	ONE(0),
	TWO(1),
	THREE(2),
	FOUR(3),
	FIVE(4),
	SIX(5),
	SEVEN(6);
	
	private int value;
	
	private WeekDay(int value) {
		this.value = value;
	}
	
	public int getDayValue() {
		return value;
	}
}
