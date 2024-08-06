package kz.hxncus.mc.minesonapi.bukkit.world;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.bukkit.event.EventManager;
import kz.hxncus.mc.minesonapi.util.FileUtil;
import lombok.EqualsAndHashCode;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
public class WorldManager {
	private final List<SimpleWorld> simpleWorlds = new ArrayList<>();
	
	public WorldManager(final MinesonAPI plugin) {
		final EventManager eventManager = plugin.getEventManager();
		this.registerEvents(eventManager);
		for (final World world : Bukkit.getWorlds()) {
			this.simpleWorlds.add(new SimpleWorld(world));
		}
	}
	
	private void registerEvents(final EventManager eventManager) {
		eventManager.register(WorldInitEvent.class, event -> {
			final World world = event.getWorld();
			if (this.simpleWorlds.stream()
			                     .noneMatch(sw -> {
				                     final World swWorld = sw.getWorld();
				                     return swWorld.equals(world);
			                     })) {
				this.simpleWorlds.add(new SimpleWorld(world));
			}
		});
		eventManager.register(WorldInitEvent.class, event -> {
			final World world = event.getWorld();
			if (this.simpleWorlds.stream()
			                     .noneMatch(sw -> sw.getWorld()
			                                        .equals(world))) {
				this.simpleWorlds.add(new SimpleWorld(world));
			}
		});
		eventManager.register(WorldUnloadEvent.class, event -> {
			final World world = event.getWorld();
			this.simpleWorlds.removeIf(sw -> sw.getWorld()
			                                   .equals(world));
		});
	}
	
	public SimpleWorld createWorld(final WorldCreator worldCreator) {
		final SimpleWorld simpleWorld = new SimpleWorld(worldCreator);
		this.simpleWorlds.add(simpleWorld);
		return simpleWorld;
	}
	
	public SimpleWorld loadWorld(final String name) {
		final World world = Bukkit.getWorld(name);
		if (world != null) {
			final SimpleWorld simpleWorld = new SimpleWorld(world);
			this.simpleWorlds.add(simpleWorld);
			return simpleWorld;
		}
		return null;
	}
	
	public boolean deleteWorld(final String name) {
		if (this.unloadWorld(name)) {
			final File worldFolder = new File(Bukkit.getWorldContainer(), name);
			try {
				FileUtil.deleteFolder(worldFolder);
				return true;
			} catch (final IOException e) {
				throw new RuntimeException("Failed to delete folder: " + worldFolder.getAbsolutePath());
			}
		}
		return false;
	}
	
	public boolean unloadWorld(final String name) {
		final World world = Bukkit.getWorld(name);
		if (world != null && Bukkit.unloadWorld(world, true)) {
			this.simpleWorlds.removeIf(sw -> sw.getWorld()
			                                   .equals(world));
			return true;
		}
		return false;
	}
	
	public List<World> getWorlds() {
		return Bukkit.getWorlds();
	}
	
	public List<SimpleWorld> getSimpleWorlds() {
		return Collections.unmodifiableList(this.simpleWorlds);
	}
	
	public void applySettingsToAllWorlds(final WorldSettings settings) {
		for (final SimpleWorld simpleWorld : this.simpleWorlds) {
			settings.apply(simpleWorld);
		}
	}
}
