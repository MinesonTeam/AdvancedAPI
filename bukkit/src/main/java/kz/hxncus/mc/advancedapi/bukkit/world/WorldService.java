package kz.hxncus.mc.advancedapi.bukkit.world;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.bukkit.event.EventService;
import kz.hxncus.mc.advancedapi.utility.FileUtil;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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

/**
 * Class World manager.
 *
 * @author GeliusIHe
 * @since 1.0.1
 */
@ToString
@EqualsAndHashCode
public class WorldService {
	private final List<SimpleWorld> simpleWorlds = new ArrayList<>(16);
	
	/**
	 * Instantiates a new World manager.
	 *
	 * @param plugin the plugin
	 */
	public WorldService(final AdvancedAPI plugin) {
		final EventService eventManager = plugin.getEventService();
		this.registerEvents(eventManager);
		for (final World world : Bukkit.getWorlds()) {
			this.simpleWorlds.add(new SimpleWorld(world));
		}
	}
	
	private void registerEvents(final EventService eventManager) {
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
	
	/**
	 * Create world simple world.
	 *
	 * @param worldCreator the world creator
	 * @return the simple world
	 */
	public SimpleWorld createWorld(final WorldCreator worldCreator) {
		final SimpleWorld simpleWorld = new SimpleWorld(worldCreator);
		this.simpleWorlds.add(simpleWorld);
		return simpleWorld;
	}
	
	/**
	 * Load world simple world.
	 *
	 * @param name the name
	 * @return the simple world
	 */
	public SimpleWorld loadWorld(final String name) {
		final World world = Bukkit.getWorld(name);
		if (world != null) {
			final SimpleWorld simpleWorld = new SimpleWorld(world);
			this.simpleWorlds.add(simpleWorld);
			return simpleWorld;
		}
		return null;
	}
	
	/**
	 * Delete world boolean.
	 *
	 * @param name the name
	 * @return the boolean
	 */
	@SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
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
	
	/**
	 * Unload world boolean.
	 *
	 * @param name the name
	 * @return the boolean
	 */
	@SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
	public boolean unloadWorld(final String name) {
		final World world = Bukkit.getWorld(name);
		if (world != null && Bukkit.unloadWorld(world, true)) {
			this.simpleWorlds.removeIf(sw -> sw.getWorld()
			                                   .equals(world));
			return true;
		}
		return false;
	}
	
	/**
	 * Gets worlds.
	 *
	 * @return the worlds
	 */
	public List<World> getWorlds() {
		return Bukkit.getWorlds();
	}
	
	/**
	 * Gets simple worlds.
	 *
	 * @return the simple worlds
	 */
	public List<SimpleWorld> getSimpleWorlds() {
		return Collections.unmodifiableList(this.simpleWorlds);
	}
	
	/**
	 * Apply settings to all worlds.
	 *
	 * @param settings the settings
	 */
	public void applySettingsToAllWorlds(final WorldSettings settings) {
		for (final SimpleWorld simpleWorld : this.simpleWorlds) {
			settings.apply(simpleWorld);
		}
	}
}
