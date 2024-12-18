package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ArrayUtil {
	public <T extends Enum<T>> T next(T[] array, T index) {
		return array[(index.ordinal() + 1) % array.length];
	}
	
	public <T extends Enum<T>> T previous(T[] array, T index) {
		int length = array.length;
		return array[(index.ordinal() - 1 + length) % length];
	}
	
	public <T extends Comparable<T>> T next(T[] array, T index) {
		int indexOf = indexOf(array, index);
		return array[(indexOf + 1) % array.length];
	}
	
	public <T extends Comparable<T>> T previous(T[] array, T index) {
		int length = array.length;
		int indexOf = indexOf(array, index);
		return array[(indexOf - 1 + length) % length];
	}
	
	public <T> T next(T[] array, T index) {
		int indexOf = indexOf(array, index);
		return array[(indexOf + 1) % array.length];
	}
	
	public <T> T previous(T[] array, T index) {
		int length = array.length;
		int indexOf = indexOf(array, index);
		return array[(indexOf - 1 + length) % length];
	}
	
	public <T> int indexOf(T[] array, T index) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == index) {
				return i;
			}
		}
		return -1;
	}
}
