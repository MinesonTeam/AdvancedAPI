package kz.hxncus.mc.advancedapi.api.registry;

import lombok.NonNull;

import java.util.Iterator;
import java.util.Map;

public interface Registry<T> extends Iterable<T> {
	@NonNull
	Map<String, T> getMap();

	default T get(@NonNull String key) {
		return this.getMap().get(key);
	}

	@NonNull
	default Iterator<T> iterator() {
		return this.getMap().values().iterator();
	}
}
