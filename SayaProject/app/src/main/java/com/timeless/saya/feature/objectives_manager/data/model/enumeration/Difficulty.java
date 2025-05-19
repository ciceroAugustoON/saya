package com.timeless.saya.feature.habitsmanager.data.model.enumeration;

public enum Difficulty {
	EASY(1, 2),
	MEDIUM(2, 5),
	HARD(3, 10);
	
	private int code;
	private int value;
	
	private Difficulty(int code, int value) {
		this.code = code;
		this.value = value;
	}
	
	public int getCode() {
		return code;
	}
	
	public int getValue() {
		return value;
	}

	public static Difficulty valueOf(int code) {
		for (Difficulty difficulty : Difficulty.values()) {
			if (difficulty.getCode() == code) {
				return difficulty;
			}
		}
		throw new IllegalArgumentException("Invalid Difficulty code");
	}
}
