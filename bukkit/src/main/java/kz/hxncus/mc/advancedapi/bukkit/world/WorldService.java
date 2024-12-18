package kz.hxncus.mc.advancedapi.bukkit.world;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.api.service.AbstractService;
import kz.hxncus.mc.advancedapi.bukkit.event.EventService;
import kz.hxncus.mc.advancedapi.service.ServiceModule;
import kz.hxncus.mc.advancedapi.utility.FileUtil;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import com.google.common.base.Optional;

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
@EqualsAndHashCode(callSuper = false)
public class WorldService extends AbstractService {
	protected static final List<AdvancedWorld> advancedWorlds = new ArrayList<>(16);
	
	/**
	 * Instantiates a new World manager.
	 *
	 * @param api the plugin
	 */
	public WorldService(final AdvancedAPI api) {
		super(api);
	}
	
	@Override
	public void register() {
		final Optional<EventService> eventServiceOptional = ServiceModule.getService(EventService.class);
		if (eventServiceOptional.isPresent()) {
			this.registerEvents(eventServiceOptional.get());
		}
		for (final World world : Bukkit.getWorlds()) {
			WorldService.advancedWorlds.add(new AdvancedWorld(world));
		}
	}
	
	@Override
	public void unregister() {
	
	}
	
	private void registerEvents(final EventService eventManager) {
		eventManager.register(WorldInitEvent.class, event -> {
			final World world = event.getWorld();
			if (WorldService.advancedWorlds.stream()
			                            .noneMatch(sw -> {
				                          final World swWorld = sw.getWorld();
				                          return swWorld.equals(world);
			                     }
			                     )) {
				WorldService.advancedWorlds.add(new AdvancedWorld(world));
			}
		});
		eventManager.register(WorldUnloadEvent.class, event -> {
			final World world = event.getWorld();
			WorldService.advancedWorlds.removeIf(sw -> sw.getWorld()
			                                     .equals(world));
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
		WorldService.advancedWorlds.add(advancedWorld);
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
			WorldService.advancedWorlds.add(advancedWorld);
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
		if (WorldService.unloadWorld(name)) {
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
			WorldService.advancedWorlds.removeIf(sw -> sw.getWorld()
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
	public static List<World> getWorlds() {
		return Bukkit.getWorlds();
	}
	
	/**
	 * Gets simple worlds.
	 *
	 * @return the simple worlds
	 */
	public static List<AdvancedWorld> getAdvancedWorlds() {
		return Collections.unmodifiableList(WorldService.advancedWorlds);
	}
	
	/**
	 * Apply settings to all worlds.
	 *
	 * @param settings the settings
	 */
	public static void applySettingsToAllWorlds(final WorldSettings settings) {
		for (final AdvancedWorld advancedWorld : WorldService.advancedWorlds) {
			settings.apply(advancedWorld);
		}
	}
}
