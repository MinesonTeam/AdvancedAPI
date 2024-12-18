package kz.hxncus.mc.advancedapi.utility.random;

import lombok.NonNull;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

import kz.hxncus.mc.advancedapi.utility.tuples.Pair;

/**
 * The type value generator.
 *
 * @param <T> the type parameter
 * @author Hxncus
 * @since  1.0.0
 */
@ToString
public class AdvancedValueGenerator<T> {
	/**
	 * The constant MAX PERCENT.
	 */
	public static final double MAX_PERCENT = 100.0;
	private final Map<T, Pair<Double, Double>> stringEntryMap = new HashMap<>(32);
	private final Random random;
	
	/**
	 * Instantiates a new value generator.
	 *
	 * @param random the random
	 */
	public AdvancedValueGenerator(final Random random) {
		this.random = random;
	}
	
	/**
	 * Instantiates a new value generator.
	 */
	public AdvancedValueGenerator() {
		this(AdvancedRandom.get());
	}

	public double generateNum() {
		return this.random.nextInt((int) MAX_PERCENT) + 1.0;
	}

	/**
	 * Generate value t.
	 *
	 * @return the t
	 */
	public T generate() {
		return this.getValue(this.generateNum());
	}
	
	/**
	 * Gets value.
	 *
	 * @param key the key
	 * @return the value
	 */
	public T getValue(final double key) {
		double val = key;
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
	 * Add value generator.
	 *
	 * @param value  the value
	 * @param chance the chance
	 * @return the value generator
	 */
	public AdvancedValueGenerator<T> add(@NonNull final T value, final double chance) {
		return this.add(value, chance, 100);
	}
	
	/**
	 * Add value generator.
	 *
	 * @param value  the value
	 * @param chance the chance
	 * @param range  the range
	 * @return the value generator
	 */
	public AdvancedValueGenerator<T> add(@NonNull final T value, final double chance, final double range) {
		if (chance > range) {
			throw new RuntimeException("chance can`t be greater than range");
		}
		this.stringEntryMap.put(value, new Pair<>(chance, range));
		return this;
	}
	
	/**
	 * Add all value generators.
	 *
	 * @param map the map
	 * @return the value generator
	 */
	public AdvancedValueGenerator<T> putAll(final @NonNull Map<? extends T, Pair<Double, Double>> map) {
		this.stringEntryMap.putAll(map);
		return this;
	}
	
	/**
	 * Remove value generator.
	 *
	 * @param value the value
	 * @return the value generator
	 */
	public AdvancedValueGenerator<T> remove(@NonNull final T value) {
		this.stringEntryMap.remove(value);
		return this;
	}
	
	/**
	 * Clear value generator.
	 *
	 * @return the value generator
	 */
	public AdvancedValueGenerator<T> clear() {
		this.stringEntryMap.clear();
		return this;
	}
}
