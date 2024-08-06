package kz.hxncus.mc.minesonapi.random;

import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public class KeyGenerator<T> {
	private final Map<T, Map.Entry<Double, Double>> stringEntryMap = new HashMap<>();
	private final Random random;
	
	public KeyGenerator(final Random random) {
		this.random = random;
	}
	
	public T generateKey() {
		return this.getKey(this.random.nextInt(100) + 1.0);
	}
	
	public T getKey(double random) {
		for (final Map.Entry<T, Double> entry : this.convertToSimilarRanges()) {
			if (random <= entry.getValue()) {
				return entry.getKey();
			}
			random = Math.round((random - entry.getValue()) * 100) / 100.0;
		}
		throw new RuntimeException("KeyGenerator is not configured correctly.");
	}
	
	private Set<AbstractMap.SimpleEntry<T, Double>> convertToSimilarRanges() {
		return this.stringEntryMap.entrySet()
		                          .stream()
		                          .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()
		                                                                                           .getKey() / entry.getValue()
		                                                                                                            .getValue() * 100))
		                          .collect(Collectors.toSet());
	}
	
	public KeyGenerator<T> add(@NonNull final T key, final double chance) {
		return this.add(key, chance, 100);
	}
	
	public KeyGenerator<T> add(@NonNull final T key, final double chance, final double range) {
		if (chance > range) {
			throw new RuntimeException("chance can`t be greater than range");
		}
		this.stringEntryMap.put(key, new AbstractMap.SimpleEntry<>(chance, range));
		return this;
	}
	
	public KeyGenerator<T> addAll(@NonNull final Map<T, Map.Entry<Double, Double>> map) {
		this.stringEntryMap.putAll(map);
		return this;
	}
	
	public KeyGenerator<T> remove(@NonNull final T key) {
		this.stringEntryMap.remove(key);
		return this;
	}
	
	public KeyGenerator<T> clear() {
		this.stringEntryMap.clear();
		return this;
	}
}
