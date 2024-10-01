package kz.hxncus.mc.minesonapi.utility;

import kz.hxncus.mc.minesonapi.utility.tuples.Pair;
import kz.hxncus.mc.minesonapi.utility.tuples.Triplet;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class Coordinate util.
 * @author Hxncus
 * @since  1.0.1
 */
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
		final int size = minValues.size();
		if (size != maxValues.size()) {
			throw new IllegalArgumentException("Number of minimum values and maximum values must be equal");
		}
		for (int i = 0; i < size; i++) {
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
	 * @param minX the minimum x-axis
	 * @param minZ the minimum z-axis
	 * @param maxX the maximum x-axis
	 * @param maxZ the maximum z-axis
	 * @throws IllegalArgumentException if minX is greater than maxX or minZ is greater than maxZ
	 */
	public void validate(final double minX, final double minZ, final double maxX, final double maxZ) {
		validate(Arrays.asList(minX, minZ), Arrays.asList(maxX, maxZ));
	}
	
	/**
	 * Calculates the distance between two sets of coordinates.
	 *
	 * @param minXZ the xz-axis of the first point
	 * @param maxXZ the xz-axis of the second point
	 * @return the distance between the two points
	 */
	public double distance(final Pair<Double, Double> minXZ, final Pair<Double, Double> maxXZ) {
		return distance(new Triplet<>(minXZ.getLeft(), 0.0D, minXZ.getRight()),
		                new Triplet<>(maxXZ.getLeft(), 0.0D, maxXZ.getRight()));
	}
	
	/**
	 * Calculates the distance between three sets of coordinates.
	 *
	 * @param minXYZ the xyz-axis of the first point
	 * @param maxXYZ the xyz-axis of the second point
	 * @return the distance between the two points
	 */
	public double distance(final Triplet<Double, Double, Double> minXYZ, final Triplet<Double, Double, Double> maxXYZ) {
		validate(minXYZ, maxXYZ);
		final double deltaX = maxXYZ.getLeft() - minXYZ.getLeft();
		final double deltaY = maxXYZ.getMiddle() - minXYZ.getMiddle();
		final double deltaZ = maxXYZ.getRight() - minXYZ.getRight();
		return Math.sqrt(StrictMath.pow(deltaX, 2) + StrictMath.pow(deltaY, 2) + StrictMath.pow(deltaZ, 2));
	}
	
	/**
	 * Validates the range of coordinates.
	 *
	 * @param minXYZ the minimum xyz-axis
	 * @param maxXYZ the maximum xyz-axis
	 * @throws IllegalArgumentException if minX is greater than maxX, or minY is greater than maxY, or minZ is greater than maxZ
	 */
	public void validate(final Triplet<Double, Double, Double> minXYZ, final Triplet<Double, Double, Double> maxXYZ) {
		validate(Arrays.asList(minXYZ.getLeft(), minXYZ.getMiddle(), minXYZ.getRight()),
		         Arrays.asList(maxXYZ.getLeft(), maxXYZ.getMiddle(), maxXYZ.getRight()));
	}
}
