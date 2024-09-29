package com.backend.saya.entities.enumeration;

public enum Difficulty {
	EASY(1),
	MEDIUM(2),
	HARD(3);
	
	private int code;
	
	private Difficulty(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
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
