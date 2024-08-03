package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.random.SimpleRandom;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
public class LocationUtil {
    public Location getRandomLocation(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        SimpleRandom random = SimpleRandom.get();
        int x = random.nextInt(maxX - minX);
        int y = random.nextInt(maxY - minY);
        int z = random.nextInt(maxZ - minZ);
        return new Location(world, x, y, z);
    }

    public Location getRandomLocation(World world, int min, int max, LocationUtil.Coordinate coordinate) {
        switch (coordinate) {
            case X -> {
                return getRandomLocation(world, min, 0, 0, max, 0, 0);
            }
            case Y -> {
                return getRandomLocation(world, 0, min, 0, 0, max, 0);
            }
            case Z -> {
                return getRandomLocation(world, 0, 0, min, 0, 0, max);
            }
            default -> {
                return getRandomLocation(world, min, min, min, max, max, max);
            }
        }
    }

    public Location getRandomLocation(World world, int minX, int minZ, int maxX, int maxZ) {
        return getRandomLocation(world, minX, 0, minZ, maxX, 0, maxZ);
    }

    public enum Coordinate {
        X, Y, Z;
    }
}
