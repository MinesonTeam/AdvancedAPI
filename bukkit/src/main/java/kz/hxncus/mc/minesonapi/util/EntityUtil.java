package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.bukkit.server.ServerManager;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.player.PlayerTeleportEvent;

@UtilityClass
public class EntityUtil {
	public static boolean isUnderWater(final Entity entity) {
		final ServerManager serverManager = MinesonAPI.getInstance()
		                                              .getServerManager();
		if (serverManager.isPaperServer() && VersionUtil.afterOrEqual(1190)) {
			//            return entity.isUnderWater();
			return false;
		} else {
			return entity.isInWater();
		}
	}
	
	public static boolean isFixed(final ItemDisplay itemDisplay) {
		return itemDisplay.getItemDisplayTransform() == ItemDisplay.ItemDisplayTransform.FIXED;
	}
	
	public static boolean isNone(final ItemDisplay itemDisplay) {
		return itemDisplay.getItemDisplayTransform() == ItemDisplay.ItemDisplayTransform.NONE;
	}
	
	public void teleport(final Location location, final Entity entity, final PlayerTeleportEvent.TeleportCause cause) {
		final ServerManager serverManager = MinesonAPI.getInstance()
		                                              .getServerManager();
		if (serverManager.isPaperServer() || serverManager.isFoliaServer() && VersionUtil.afterOrEqual(1194)) {
			//            entity.teleportAsync(location, cause);
			entity.teleport(location);
		} else {
			entity.teleport(location);
		}
	}
	
	public static void teleport(final Location location, final Entity entity) {
		final ServerManager serverManager = MinesonAPI.getInstance()
		                                              .getServerManager();
		if (VersionUtil.afterOrEqual(1194) && (serverManager.isPaperServer() || serverManager.isFoliaServer())) {
			//            entity.teleportAsync(location);
			entity.teleport(location);
		} else {
			entity.teleport(location);
		}
	}
	
}
