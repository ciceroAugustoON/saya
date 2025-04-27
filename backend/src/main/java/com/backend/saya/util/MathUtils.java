package com.backend.saya.util;

import java.util.Arrays;

public class MathUtils {
	
	public static int sumIntegerArray(int[] array) {
		int sum = 0;
		for(int value : array) {
			sum += value;
		}
		return sum;
	}

	public static int findMaxValueIndex(int[] array) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException("Array must not be empty or null");
		}

		int maxIndex = 0;
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
				maxIndex = i;
			}
		}
		int min = Arrays.stream(array).min().getAsInt();

		return (max == min)? -1 : maxIndex;
	}

}
