package kz.hxncus.mc.minesonapi.random;

import it.unimi.dsi.util.XoRoShiRo128PlusPlusRandom;
import lombok.NonNull;
import org.jetbrains.annotations.Range;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MSRandom {
    private static final XoRoShiRo128PlusPlusRandom random = new XoRoShiRo128PlusPlusRandom();
    protected final Map<String, Map.Entry<Double, Double>> stringEntryMap = new HashMap<>();
    private static MSRandom instance;
    private MSRandom() {
    }

    public static MSRandom getInstance() {
        if (instance == null) {
            instance = new MSRandom();
        }
        return instance;
    }

    public static float nextFloat() {
        return MSRandom.random.nextFloat();
    }

    public static float nextFloat(float bound) {
        return MSRandom.random.nextFloat(bound);
    }

    public static float nextFloat(float origin, float bound) {
        return MSRandom.random.nextFloat(origin, bound);
    }

    public static double nextDouble(double bound) {
        return MSRandom.random.nextDouble(bound);
    }

    public static double nextDouble(double origin, double bound) {
        return MSRandom.random.nextDouble(origin, bound);
    }

    public static double nextDoubleFast() {
        return MSRandom.random.nextDoubleFast();
    }

    public static int nextInt() {
        return (int) MSRandom.nextLong();
    }

    public static int nextInt(int bound) {
        return (int) MSRandom.nextLong(bound);
    }

    public static int nextInt(int origin, int bound) {
        return MSRandom.random.nextInt(origin, bound);
    }

    public static long nextLong() {
        return MSRandom.random.nextLong();
    }

    public static long nextLong(long bound) {
        if (bound <= 0) {
            return 0;
        }
        return MSRandom.random.nextLong(bound);
    }

    public static long nextLong(long origin, long bound) {
        return MSRandom.random.nextLong(origin, bound);
    }

    public static boolean nextBoolean() {
        return MSRandom.nextLong() < 0L;
    }

    public static void nextBytes(byte[] bytes) {
        MSRandom.random.nextBytes(bytes);
    }

    public String generateRandomKey() {
        return getKeyFromEntryMap(this.nextInt(100) + 1.0);
    }

    public String getKeyFromEntryMap(@Range(from = 1, to = 100) double random) {
        Set<AbstractMap.SimpleEntry<String, Double>> convertedMap = convertToSimilarRanges();

        for (Map.Entry<String, Double> entry : convertedMap) {
            double value = entry.getValue();
            if (random <= value) {
                return entry.getKey();
            }
            random = Math.round((random - value) * 100.0) / 100.0;
        }

        throw new RuntimeException("Class random is not configured correctly");
    }

    private Set<AbstractMap.SimpleEntry<String, Double>> convertToSimilarRanges() {
        return this.stringEntryMap.entrySet().stream().map(entry -> {
            Map.Entry<Double, Double> value = entry.getValue();
            double multiply = value.getKey() / value.getValue();
            return new AbstractMap.SimpleEntry<>(entry.getKey(), multiply * 100);
        }).collect(Collectors.toSet());
    }

    public MSRandom add(@NonNull String key, double chance) {
        return add(key, chance, 100);
    }

    public MSRandom add(@NonNull String key, double chance, double range) {
        if (chance > range) {
            throw new RuntimeException("chance can`t be greater than range");
        }
        this.stringEntryMap.put(key, new AbstractMap.SimpleEntry<>(chance, range));
        return this;
    }

    public MSRandom addAll(@NonNull Map<String, Map.Entry<Double, Double>> map) {
        this.stringEntryMap.putAll(map);
        return this;
    }

    public MSRandom remove(@NonNull String key) {
        this.stringEntryMap.remove(key);
        return this;
    }

    public MSRandom clear() {
        this.stringEntryMap.clear();
        return this;
    }
}
