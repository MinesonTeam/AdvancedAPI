package kz.hxncus.mc.advancedapi.bukkit.world;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.annotation.Inject;
import kz.hxncus.mc.advancedapi.annotation.OnPluginEnable;
import kz.hxncus.mc.advancedapi.bukkit.event.EventDispatcher;
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
 * Class World controller.
 *
 * @author GeliusIHe
 * @since 1.0.1
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class WorldController {
	protected static final List<AdvancedWorld> advancedWorlds = new ArrayList<>(16);
	@Inject
	private AdvancedAPI plugin;

	@OnPluginEnable
	public void onPluginEnable() {
		final EventDispatcher eventDispatcher = new EventDispatcher(plugin);
		this.registerEvents(eventDispatcher);
		for (final World world : Bukkit.getWorlds()) {
			WorldController.advancedWorlds.add(new AdvancedWorld(world));
		}
	}

	private void registerEvents(final EventDispatcher eventDispatcher) {
		eventDispatcher.register(WorldInitEvent.class, event -> {
			final World world = event.getWorld();
			boolean isExists = WorldController.advancedWorlds.stream()
															.anyMatch(sw -> {
																	final World swWorld = sw.getWorld();
																	return swWorld.equals(world);
															});
			if (!isExists) {
				WorldController.advancedWorlds.add(new AdvancedWorld(world));
			}
		});
		eventDispatcher.register(WorldUnloadEvent.class, event -> {
			final World world = event.getWorld();
			WorldController.advancedWorlds.removeIf(sw -> sw.getWorld().equals(world));
		});
	}
	
	/**
	 * Create world simple world.
	 *
	 * @param worldCreator the world creator
	 * @return the simple world
	 */
	public static AdvancedWorld createWorld(final WorldCreator worldCreator) {
		final AdvancedWorld advancedWorld = new AdvancedWorld(worldCreator);
		WorldController.advancedWorlds.add(advancedWorld);
		return advancedWorld;
	}
	
	/**
	 * Load world simple world.
	 *
	 * @param name the name
	 * @return the simple world
	 */
	public static AdvancedWorld loadWorld(final String name) {
		final World world = Bukkit.getWorld(name);
		if (world != null) {
			final AdvancedWorld advancedWorld = new AdvancedWorld(world);
			WorldController.advancedWorlds.add(advancedWorld);
			return advancedWorld;
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
	public static boolean deleteWorld(final String name) {
		if (WorldController.unloadWorld(name)) {
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
	public static boolean unloadWorld(final String name) {
		final World world = Bukkit.getWorld(name);
		if (world != null && Bukkit.unloadWorld(world, true)) {
			WorldController.advancedWorlds.removeIf(sw -> sw.getWorld().equals(world));
			return true;
		}
		return false;
	}
	
	/**
	 * Gets worlds.
	 *
	 * @return the worlds
	 */
	public static List<World> getWorlds() {
		return Bukkit.getWorlds();
	}
	
	/**
	 * Gets simple worlds.
	 *
	 * @return the simple worlds
	 */
	public static List<AdvancedWorld> getAdvancedWorlds() {
		return Collections.unmodifiableList(WorldController.advancedWorlds);
	}
	
	/**
	 * Apply settings to all worlds.
	 *
	 * @param settings the settings
	 */
	public static void applySettingsToAllWorlds(final WorldSettings settings) {
		for (final AdvancedWorld advancedWorld : WorldController.advancedWorlds) {
			settings.apply(advancedWorld);
		}
	}
}
