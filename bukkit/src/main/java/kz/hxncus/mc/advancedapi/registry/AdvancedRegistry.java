package kz.hxncus.mc.advancedapi.registry;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import kz.hxncus.mc.advancedapi.api.registry.Registry;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Getter
public class AdvancedRegistry<T> implements Registry<T> {
	@Getter
	private static final Map<Class<?>, Registry<?>> registries = new HashMap<>();
	private final Map<String, T> map;
	
	public AdvancedRegistry(@NonNull Class<T> type, @NonNull Predicate<T> predicate) {
		ImmutableMap.Builder<String, T> builder = ImmutableMap.builder();
		
		for (T entry : type.getEnumConstants()) {
			if (predicate.test(entry)) {
				builder.put(entry.toString(), entry);
			}
		}
		
		this.map = builder.build();
	}
	
	public AdvancedRegistry(@NonNull Class<T> type) {
		this(type, Predicates.alwaysTrue());
	}
	
	@SuppressWarnings("unchecked")
	public static <V> Registry<V> getRegistry(Class<V> clazz) {
		Registry<?> registry = AdvancedRegistry.getRegistries().get(clazz);
		if (registry == null) {
			return null;
		}
		return (Registry<V>) registry;
	}
	
	public static <V> void addRegistry(@NonNull Class<V> key, @NonNull Registry<V> registry) {
		AdvancedRegistry.getRegistries().put(key, registry);
	}
}
