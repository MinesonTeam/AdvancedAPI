package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ArrayUtil {
	public <T extends Enum<T>> T next(T[] array, T val) {
		return array[(val.ordinal() + 1) % array.length];
	}
	
	public <T extends Enum<T>> T previous(T[] array, T val) {
		int length = array.length;
		return array[(val.ordinal() - 1 + length) % length];
	}
	
	public <T> T next(T[] array, T val) {
		int index = indexOf(array, val);
		return array[(index + 1) % array.length];
	}
	
	public <T> T previous(T[] array, T val) {
		int length = array.length;
		int index = indexOf(array, val);
		return array[(index - 1 + length) % length];
	}

	public <T> int indexOf(T[] array, T val) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == val) {
				return i;
			}
		}
		return -1;
	}

	public int indexOf(int[] array, int val) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == val) {
				return i;
			}
		}
		return -1;
	}

	public <T> boolean contains(T[] array, T val) {
		return indexOf(array, val) != -1;
	}

	public boolean contains(int[] array, int val) {
		return indexOf(array, val) != -1;
	}
}
