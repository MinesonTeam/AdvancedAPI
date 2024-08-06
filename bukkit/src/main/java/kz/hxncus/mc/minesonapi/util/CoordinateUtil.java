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
	 * @throws IllegalArgumentException if minX is greater than maxX
	 */
	public void validate(final double minX, final double maxX) {
		validate(Collections.singletonList(minX), Collections.singletonList(maxX));
	}
	
	/**
	 * Validates the range of coordinates.
	 *
	 * @param minValues the minimum coordinates
	 * @param maxValues the maximum coordinates
	 * @throws IllegalArgumentException if any minimum value is greater than the corresponding maximum value
	 */
	public void validate(final List<Double> minValues, final List<Double> maxValues) {
		if (minValues.size() != maxValues.size()) {
			throw new IllegalArgumentException("Number of minimum values and maximum values must be equal");
		}
		for (int i = 0; i < minValues.size(); i++) {
			final double min = minValues.get(i);
			final double max = maxValues.get(i);
			if (min > max) {
				throw new IllegalArgumentException("Minimum value " + min + " is greater than maximum value " + max + ". Index " + i);
			}
		}
	}
	
	/**
	 * Validates the range of coordinates.
	 *
	 * @param minX the minimum x-coordinate
	 * @param minZ the minimum z-coordinate
	 * @param maxX the maximum x-coordinate
	 * @param maxZ the maximum z-coordinate
	 * @throws IllegalArgumentException if minX is greater than maxX or minZ is greater than maxZ
	 */
	public void validate(final double minX, final double minZ, final double maxX, final double maxZ) {
		validate(Arrays.asList(minX, minZ), Arrays.asList(maxX, maxZ));
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
	public double distance(final double minX, final double minZ, final double maxX, final double maxZ) {
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
	public double distance(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
		validate(minX, minY, minZ, maxX, maxY, maxZ);
		final double deltaX = maxX - minX;
		final double deltaY = maxY - minY;
		final double deltaZ = maxZ - minZ;
		return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));
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
	 * @throws IllegalArgumentException if minX is greater than maxX, or minY is greater than maxY, or minZ is greater than maxZ
	 */
	public void validate(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
		validate(Arrays.asList(minX, minY, minZ), Arrays.asList(maxX, maxY, maxZ));
	}
}
