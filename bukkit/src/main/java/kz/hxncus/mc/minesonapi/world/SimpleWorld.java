package kz.hxncus.mc.minesonapi.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class SimpleWorld {
    private World world;

    public SimpleWorld(String worldName) {
        this.world = getOrCreateWorld(worldName);
    }

    public SimpleWorld(World world) {
        this.world = world;
    }

    public World getOrCreateWorld(String worldName) {
        World newWorld = world;
        if (newWorld != null) {
            return newWorld;
        }
        synchronized(World.class) {
            if (world == null) {
                world = Bukkit.createWorld(new WorldCreator(worldName));
            }
        }
        return world;
    }
}
