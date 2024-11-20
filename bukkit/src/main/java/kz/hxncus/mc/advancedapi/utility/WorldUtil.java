package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

@UtilityClass
public class WorldUtil {
	public World getOrCreateWorld(final WorldCreator worldCreator) {
		World world = Bukkit.getWorld(worldCreator.name());
		if (world == null) {
			world = worldCreator.createWorld();
		}
		return world;
	}
}
