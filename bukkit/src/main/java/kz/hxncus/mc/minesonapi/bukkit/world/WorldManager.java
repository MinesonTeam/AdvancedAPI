package kz.hxncus.mc.minesonapi.bukkit.world;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.bukkit.event.EventManager;
import org.bukkit.*;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldManager {
    private final List<SimpleWorld> simpleWorlds = new ArrayList<>();

    public WorldManager(MinesonAPI plugin) {
        registerEvents(plugin.getEventManager());
        for (World world : Bukkit.getWorlds()) {
            simpleWorlds.add(new SimpleWorld(world));
        }
    }

    public SimpleWorld createWorld(String name) {
        SimpleWorld simpleWorld = new SimpleWorld(name);
        simpleWorlds.add(simpleWorld);
        return simpleWorld;
    }

    public SimpleWorld createWorld(String name, World.Environment environment, WorldType type, ChunkGenerator generator) {
        SimpleWorld simpleWorld = new SimpleWorld(name, environment, type, generator);
        simpleWorlds.add(simpleWorld);
        return simpleWorld;
    }

    public SimpleWorld loadWorld(String name) {
        World world = Bukkit.getWorld(name);
        if (world != null) {
            SimpleWorld simpleWorld = new SimpleWorld(world);
            simpleWorlds.add(simpleWorld);
            return simpleWorld;
        }
        return null;
    }

    public boolean unloadWorld(String name) {
        World world = Bukkit.getWorld(name);
        if (world != null && Bukkit.unloadWorld(world, true)) {
            simpleWorlds.removeIf(sw -> sw.getWorld().equals(world));
            return true;
        }
        return false;
    }

    public boolean deleteWorld(String name) {
        if (unloadWorld(name)) {
            File worldFolder = new File(Bukkit.getWorldContainer(), name);
            try {
                deleteFolder(worldFolder);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void deleteFolder(File file) throws IOException {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                deleteFolder(subFile);
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete file: " + file.getAbsolutePath());
        }
    }

    public List<World> getWorlds() {
        return Bukkit.getWorlds();
    }

    public List<SimpleWorld> getAllSimpleWorlds() {
        return new ArrayList<>(simpleWorlds);
    }

    public void applySettingsToAllWorlds(GameMode gameMode, Difficulty difficulty, boolean storm, long time) {
        for (SimpleWorld simpleWorld : simpleWorlds) {
            simpleWorld.gameMode(gameMode)
                    .difficulty(difficulty)
                    .storm(storm)
                    .time(time);
        }
    }

    public void registerEvents(EventManager eventManager) {
        eventManager.register(WorldInitEvent.class, event -> {
            World world = event.getWorld();
            if (simpleWorlds.stream().noneMatch(sw -> sw.getWorld().equals(world))) {
                simpleWorlds.add(new SimpleWorld(world));
            }
        });
        eventManager.register(WorldInitEvent.class, event -> {
            World world = event.getWorld();
            if (simpleWorlds.stream().noneMatch(sw -> sw.getWorld().equals(world))) {
                simpleWorlds.add(new SimpleWorld(world));
            }
        });
        eventManager.register(WorldUnloadEvent.class, event -> {
            World world = event.getWorld();
            simpleWorlds.removeIf(sw -> sw.getWorld().equals(world));
        });
    }


}
