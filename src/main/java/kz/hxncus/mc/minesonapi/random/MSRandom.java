package kz.hxncus.mc.minesonapi.random;

import it.unimi.dsi.util.XoRoShiRo128PlusPlusRandom;
import lombok.NonNull;
import org.jetbrains.annotations.Range;

import java.util.*;
import java.util.stream.Collectors;

public class MSRandom extends Random {
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

    @Override
    public float nextFloat() {
        return MSRandom.random.nextFloat();
    }

    @Override
    public float nextFloat(float bound) {
        return MSRandom.random.nextFloat(bound);
    }

    @Override
    public float nextFloat(float origin, float bound) {
        return MSRandom.random.nextFloat(origin, bound);
    }

    @Override
    public double nextDouble(double bound) {
        return MSRandom.random.nextDouble(bound);
    }

    @Override
    public double nextDouble(double origin, double bound) {
        return MSRandom.random.nextDouble(origin, bound);
    }

    public double nextDoubleFast() {
        return MSRandom.random.nextDoubleFast();
    }

    @Override
    public int nextInt() {
        return MSRandom.random.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return MSRandom.random.nextInt(bound);
    }

    @Override
    public int nextInt(int origin, int bound) {
        return MSRandom.random.nextInt(origin, bound);
    }

    @Override
    public long nextLong() {
        return MSRandom.random.nextLong();
    }

    @Override
    public long nextLong(long bound) {
        return MSRandom.random.nextLong(bound);
    }

    @Override
    public long nextLong(long origin, long bound) {
        return MSRandom.random.nextLong(origin, bound);
    }

    @Override
    public boolean nextBoolean() {
        return MSRandom.random.nextBoolean();
    }

    @Override
    public void nextBytes(byte[] bytes) {
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
