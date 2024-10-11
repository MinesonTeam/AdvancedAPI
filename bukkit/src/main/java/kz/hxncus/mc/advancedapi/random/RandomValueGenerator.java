package kz.hxncus.mc.advancedapi.random;

import lombok.NonNull;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Key generator.
 *
 * @param <T> the type parameter
 * @author Hxncus
 * @since  1.0.0
 */
@ToString
public class RandomValueGenerator<T> {
	/**
	 * The constant MAX PERCENT.
	 */
	public static final double MAX_PERCENT = 100.0;
	private final Map<T, Map.Entry<Double, Double>> stringEntryMap = new HashMap<>(32);
	private final Random random;
	
	/**
	 * Instantiates a new Key generator.
	 *
	 * @param random the random
	 */
	public RandomValueGenerator(final Random random) {
		this.random = random;
	}
	
	/**
	 * Generate key t.
	 *
	 * @return the t
	 */
	public T generateKey() {
		return this.getKey(this.random.nextInt((int) MAX_PERCENT) + 1.0);
	}
	
	/**
	 * Gets key.
	 *
	 * @param random the random
	 * @return the key
	 */
	public T getKey(final double random) {
		double val = random;
		for (final Map.Entry<T, Double> entry : this.convertToSimilarRanges()) {
			if (val <= entry.getValue()) {
				return entry.getKey();
			}
			val = Math.round((val - entry.getValue()) * MAX_PERCENT) / MAX_PERCENT;
		}
		throw new RuntimeException("KeyGenerator is not configured correctly.");
	}
	
	private Set<AbstractMap.SimpleEntry<T, Double>> convertToSimilarRanges() {
		return this.stringEntryMap.entrySet().stream()
		                          .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(),
		                              entry.getValue().getKey() / entry.getValue().getValue() * 100))
		                          .collect(Collectors.toSet());
	}
	
	/**
	 * Add key generator.
	 *
	 * @param key    the key
	 * @param chance the chance
	 * @return the key generator
	 */
	public RandomValueGenerator<T> add(@NonNull final T key, final double chance) {
		return this.add(key, chance, 100);
	}
	
	/**
	 * Add key generator.
	 *
	 * @param key    the key
	 * @param chance the chance
	 * @param range  the range
	 * @return the key generator
	 */
	public RandomValueGenerator<T> add(@NonNull final T key, final double chance, final double range) {
		if (chance > range) {
			throw new RuntimeException("chance can`t be greater than range");
		}
		this.stringEntryMap.put(key, new AbstractMap.SimpleEntry<>(chance, range));
		return this;
	}
	
	/**
	 * Add all key generators.
	 *
	 * @param map the map
	 * @return the key generator
	 */
	public RandomValueGenerator<T> addAll(final @NonNull Map<? extends T, ? extends Map.Entry<Double, Double>> map) {
		this.stringEntryMap.putAll(map);
		return this;
	}
	
	/**
	 * Remove key generator.
	 *
	 * @param key the key
	 * @return the key generator
	 */
	public RandomValueGenerator<T> remove(@NonNull final T key) {
		this.stringEntryMap.remove(key);
		return this;
	}
	
	/**
	 * Clear key generator.
	 *
	 * @return the key generator
	 */
	public RandomValueGenerator<T> clear() {
		this.stringEntryMap.clear();
		return this;
	}
}
