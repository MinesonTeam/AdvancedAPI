package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class ArrayUtil {
	public <T extends Enum<T>> T next(T[] array, T from) {
		return array[(from.ordinal() + 1) % array.length];
	}
	
	public <T extends Enum<T>> T previous(T[] array, T from) {
		int length = array.length;
		return array[(from.ordinal() - 1 + length) % length];
	}
	
	public <T extends Comparable<T>> T next(T[] array, T from) {
		int index = Arrays.binarySearch(array, from);
		return array[(index + 1) % array.length];
	}
	
	public <T extends Comparable<T>> T previous(T[] array, T from) {
		int length = array.length;
		int index = Arrays.binarySearch(array, from);
		return array[(index - 1 + length) % length];
	}
	
	public <T> T next(T[] array, T from) {
		int index = indexOf(array, from);
		return array[(index + 1) % array.length];
	}
	
	public <T> T previous(T[] array, T from) {
		int length = array.length;
		int index = indexOf(array, from);
		return array[(index - 1 + length) % length];
	}
	
	public <T> int indexOf(T[] array, T from) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == from) {
				return i;
			}
		}
		return -1;
	}
}
