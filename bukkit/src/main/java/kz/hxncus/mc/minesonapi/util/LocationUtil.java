package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.random.SimpleRandom;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
public class LocationUtil {
	public Location getRandomLocation(final World world, final int min, final int max, final LocationUtil.Coordinate coordinate) {
		switch (coordinate) {
			case X: {
				return getRandomLocation(world, min, 0, 0, max, 0, 0);
			}
			case Y: {
				return getRandomLocation(world, 0, min, 0, 0, max, 0);
			}
			case Z: {
				return getRandomLocation(world, 0, 0, min, 0, 0, max);
			}
			default: {
				return getRandomLocation(world, min, min, min, max, max, max);
			}
		}
	}
	
	public Location getRandomLocation(final World world, final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ) {
		final SimpleRandom random = SimpleRandom.get();
		final int x = random.nextInt(maxX - minX);
		final int y = random.nextInt(maxY - minY);
		final int z = random.nextInt(maxZ - minZ);
		return new Location(world, x, y, z);
	}
	
	public Location getRandomLocation(final World world, final int minX, final int minZ, final int maxX, final int maxZ) {
		return getRandomLocation(world, minX, 0, minZ, maxX, 0, maxZ);
	}
	
	public enum Coordinate {
		X, Y, Z
	}
}
