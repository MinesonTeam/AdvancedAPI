package kz.hxncus.mc.advancedapi.registry;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import kz.hxncus.mc.advancedapi.api.registry.Registry;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

public class AdvancedRegistry<T> implements Registry<T> {
	private static final Map<Class<?>, Registry<?>> registries = new HashMap<>();
	private final Map<String, T> map;
	
	public AdvancedRegistry(@NonNull Class<T> type) {
		this(type, Predicates.alwaysTrue());
	}
	
	public AdvancedRegistry(@NonNull Class<T> type, @NonNull Predicate<T> predicate) {
		ImmutableMap.Builder<String, T> builder = ImmutableMap.builder();
		
		for (T entry : type.getEnumConstants()) {
			if (predicate.test(entry)) {
				builder.put(entry.toString(), entry);
			}
		}
		
		map = builder.build();
	}
	
	@Nullable
	public static <V> Registry<V> getRegistry(Class<V> clazz) {
		Registry<?> registry = registries.get(clazz);
		if (registry == null) {
			return null;
		}
		return (Registry<V>) registry;
	}
	
	public static <V> void addRegistry(@NonNull Class<V> key, @NonNull Registry<V> registry) {
		AdvancedRegistry.registries.put(key, registry);
	}
	
	@Nullable
	@Override
	public T get(@NonNull final String key) {
		return map.get(key);
	}
	
	@NonNull
	@Override
	public Iterator<T> iterator() {
		return map.values().iterator();
	}
}
