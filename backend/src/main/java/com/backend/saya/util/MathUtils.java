package com.backend.saya.util;

public class MathUtils {
	
	public static int sumIntegerArray(int[] array) {
		int sum = 0;
		for(int value : array) {
			sum += value;
		}
		return sum;
	}

}
