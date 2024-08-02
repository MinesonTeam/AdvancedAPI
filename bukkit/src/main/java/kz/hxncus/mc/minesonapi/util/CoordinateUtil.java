package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class CoordinateUtil {
    /**
     * Validates the range of coordinates.
     *
     * @param minX the minimum x-coordinate
     * @param maxX the maximum x-coordinate
     *
     * @throws IllegalArgumentException if minX is greater than maxX
     */
    public void validate(double minX, double maxX) {
        validate(Collections.singletonList(minX), Collections.singletonList(maxX));
    }

    /**
     * Validates the range of coordinates.
     *
     * @param minX the minimum x-coordinate
     * @param minZ the minimum z-coordinate
     * @param maxX the maximum x-coordinate
     * @param maxZ the maximum z-coordinate
     *
     * @throws IllegalArgumentException if minX is greater than maxX or minZ is greater than maxZ
     */
    public void validate(double minX, double minZ, double maxX, double maxZ) {
        validate(Arrays.asList(minX, minZ), Arrays.asList(maxX, maxZ));
    }

    /**
     * Validates the range of coordinates.
     *
     * @param minX the minimum x-coordinate
     * @param minY the minimum y-coordinate
     * @param minZ the minimum z-coordinate
     * @param maxX the maximum x-coordinate
     * @param maxY the maximum y-coordinate
     * @param maxZ the maximum z-coordinate
     *
     * @throws IllegalArgumentException if minX is greater than maxX, or minY is greater than maxY, or minZ is greater than maxZ
     */
    public void validate(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        validate(Arrays.asList(minX, minY, minZ), Arrays.asList(maxX, maxY, maxZ));
    }

    /**
     * Validates the range of coordinates.
     *
     * @param minValues the minimum coordinates
     * @param maxValues the maximum coordinates
     *
     * @throws IllegalArgumentException if any minimum value is greater than the corresponding maximum value
     */
    public void validate(List<Double> minValues, List<Double> maxValues) {
        if (minValues.size() != maxValues.size()) {
            throw new IllegalArgumentException("Number of minimum values and maximum values must be equal");
        }
        for (int i = 0; i < minValues.size(); i++) {
            double min = minValues.get(i);
            double max = maxValues.get(i);
            if (min > max) {
                throw new IllegalArgumentException("Minimum value " + min + " is greater than maximum value " + max + ". Index " + i);
            }
        }
    }

    /**
     * Calculates the distance between two sets of coordinates.
     *
     * @param minX the x-coordinate of the first point
     * @param minZ the z-coordinate of the first point
     * @param maxX the x-coordinate of the second point
     * @param maxZ the z-coordinate of the second point
     * @return the distance between the two points
     */
    public double distance(double minX, double minZ, double maxX, double maxZ) {
        return distance(minX, 0, minZ, maxX, 0, maxZ);
    }

    /**
     * Calculates the distance between three sets of coordinates.
     *
     * @param minX the x-coordinate of the first point
     * @param minZ the z-coordinate of the first point
     * @param maxX the x-coordinate of the second point
     * @param maxZ the z-coordinate of the second point
     * @return the distance between the two points
     */
    public double distance(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        validate(minX, minY, minZ, maxX, maxY, maxZ);
        double deltaX = maxX - minX;
        double deltaY = maxY - minY;
        double deltaZ = maxZ - minZ;
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));
    }
}
