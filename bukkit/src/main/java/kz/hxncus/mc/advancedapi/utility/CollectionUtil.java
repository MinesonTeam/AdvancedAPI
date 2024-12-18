package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public final class CollectionUtil {
	public <T> T next(Collection<T> types, T from) {
		int index = indexOf(types, from);
		return get(types, (index + 1) % types.size());
	}
	
	public <T> T previous(Collection<T> types, T from) {
		int index = indexOf(types, from);
		return get(types, (index - 1 + types.size()) % types.size());
	}
	
	public <T> int indexOf(Collection<T> types, T o) {
		return indexOfRange(types,o, 0, types.size());
	}
	
	public <T> int indexOfRange(Collection<T> types, T o, int start, int end) {
		Object[] es = types.toArray();
		if (o == null) {
			for (int i = start; i < end; i++) {
				if (es[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = start; i < end; i++) {
				if (o.equals(es[i])) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public <T> T get(Collection<T> types, int index) {
		int i = 0;
		for (T type : types) {
			if (i++ == index) {
				return type;
			}
		}
		return null;
	}
	
	public <T> T first(Collection<T> types) {
		for (T type : types) {
			return type;
		}
		return null;
	}
	
	public <T> T last(Collection<T> types) {
		return get(types, types.size() - 1);
	}
}
