package kz.hxncus.mc.advancedapi.utility;

import kz.hxncus.mc.advancedapi.utility.random.AdvancedRandom;
import kz.hxncus.mc.advancedapi.utility.tuples.Pair;
import kz.hxncus.mc.advancedapi.utility.tuples.Triplet;
import lombok.experimental.UtilityClass;
import org.bukkit.Axis;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * The type Location util.
 * @author Hxncus
 * @since 1.0.0
 */
@UtilityClass
public final class LocationUtil {
	public Location floorBlock(Location location) {
		location.setX(location.getBlockX());
		location.setY(location.getBlockY());
		location.setZ(location.getBlockZ());
		return location;
	}
	
	public Location floorCenterBlock(Location location) {
		location.setX(location.getBlockX() + 0.5D);
		location.setY(location.getBlockY());
		location.setZ(location.getBlockZ() + 0.5D);
		return location;
	}
	
	public Location resetLook(Location location) {
		location.setYaw(0.0F);
		location.setPitch(0.0F);
		return location;
	}
	
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
		final AdvancedRandom random = AdvancedRandom.get();
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
	
	public boolean isInChunk(Chunk chunk, Location location) {
		return chunk.getX() == location.getBlockX() >> 4 && chunk.getZ() == location.getBlockZ() >> 4;
	}
	
	public boolean isInChunk(Chunk chunk, Location ... locations) {
		for (Location location : locations) {
			if (!isInChunk(chunk, location)) {
				return false;
			}
		}
		return true;
	}
	
	public Location fixNaN(Location location) {
		if (Double.isNaN(location.getX()))
			location.setX(0.0D);
		if (Double.isNaN(location.getY()))
			location.setY(0.0D);
		if (Double.isNaN(location.getZ()))
			location.setZ(0.0D);
		if (Float.isNaN(location.getYaw()))
			location.setYaw(0.0F);
		if (Float.isNaN(location.getPitch()))
			location.setPitch(0.0F);
		return location;
	}
	
	public Location fixAngle(Location location) {
		float angle = location.getYaw();
		while (angle < 0.0F) {
			angle += 360.0F;
		}
		while (angle >= 360.0F) {
			angle -= 360.0F;
		}
		location.setYaw(angle);
		return location;
	}
	
	public BlockFace getDirectionFace(Location location) {
		fixAngle(location);
		float yaw = location.getYaw();
		if (yaw >= 225.0F && yaw <= 315.0F)
			return BlockFace.EAST;
		if ((yaw >= 315.0F && yaw <= 360.0F) || (yaw >= 0.0F && yaw <= 45.0F))
			return BlockFace.SOUTH;
		if (yaw >= 45.0F && yaw <= 135.0F)
			return BlockFace.WEST;
		if (yaw >= 135.0F && yaw <= 225.0F)
			return BlockFace.NORTH;
		throw new AssertionError();
	}
	
	// Добавить метод для проверки безопасности локации
	public static boolean isSafeLocation(Location location) {
		Block feet = location.getBlock();
		Block head = feet.getRelative(BlockFace.UP);
		Block ground = feet.getRelative(BlockFace.DOWN);
		return !feet.getType().isSolid() 
			&& !head.getType().isSolid()
			&& ground.getType().isSolid();
	}
	
	// Добавить метод для поиска ближайшей безопасной локации
	public static Location findSafeLocation(Location location, int radius) {
		World world = location.getWorld();
		int startX = location.getBlockX() - radius;
		int startZ = location.getBlockZ() - radius;
		
		for (int x = startX; x <= startX + radius * 2; x++) {
			for (int z = startZ; z <= startZ + radius * 2; z++) {
				Location loc = world.getHighestBlockAt(x, z).getLocation();
				if (isSafeLocation(loc)) {
					return loc.add(0.5, 0, 0.5);
				}
			}
		}
		return null;
	}
}
