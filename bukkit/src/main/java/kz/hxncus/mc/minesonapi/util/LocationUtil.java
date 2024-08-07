package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.random.SimpleRandom;
import kz.hxncus.mc.minesonapi.util.tuples.Pair;
import kz.hxncus.mc.minesonapi.util.tuples.Triplet;
import lombok.experimental.UtilityClass;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * The type Location util.
 * @author Hxncus
 * @since 1.0.0
 */
@UtilityClass
public class LocationUtil {
	/**
	 * Gets random location.
	 *
	 * @param world      the world
	 * @param min        the min
	 * @param max        the max
	 * @param axis       the axis
	 * @return the random location
	 */
	public Location getRandomLocation(final World world, final int min, final int max, final Axis axis) {
		return switch (axis) {
			case X -> getRandomLocation(world, new Triplet<>(min, 0, 0), new Triplet<>(max, 0, 0));
			case Y -> getRandomLocation(world, new Triplet<>(0, min, 0), new Triplet<>(0, max, 0));
			case Z -> getRandomLocation(world, new Triplet<>(0, 0, min), new Triplet<>(0, 0, max));
		};
	}
	
	/**
	 * Gets random location.
	 *
	 * @param world the world
	 * @param minXYZ  the min x and min y and min z
	 * @param maxXYZ  the max x and max y and max z
	 * @return the random location
	 */
	public Location getRandomLocation(final World world, final Triplet<Integer, Integer, Integer> minXYZ, final Triplet<Integer, Integer, Integer> maxXYZ) {
		final SimpleRandom random = SimpleRandom.get();
		final int x = random.nextInt(maxXYZ.getLeft() - minXYZ.getLeft());
		final int y = random.nextInt(maxXYZ.getMiddle() - minXYZ.getMiddle());
		final int z = random.nextInt(maxXYZ.getRight() - minXYZ.getRight());
		return new Location(world, x, y, z);
	}
	
	/**
	 * Gets random location.
	 *
	 * @param world the world
	 * @param minXZ  the min x and min z
	 * @param maxXZ  the max x and max z
	 * @return the random location
	 */
	public Location getRandomLocation(final World world, final Pair<Integer, Integer> minXZ, final Pair<Integer, Integer> maxXZ) {
		return getRandomLocation(world, new Triplet<>(minXZ.getLeft(), 0, minXZ.getRight()),
		                               new Triplet<>(maxXZ.getLeft(), 0, maxXZ.getRight()));
	}
}
