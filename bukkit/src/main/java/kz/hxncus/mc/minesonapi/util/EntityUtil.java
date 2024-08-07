package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.bukkit.server.ServerManager;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Class Entity util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public class EntityUtil {
	/**
	 * Is underwater boolean.
	 *
	 * @param entity the entity
	 * @return the boolean
	 */
	public boolean isUnderWater(final Entity entity) {
		final ServerManager serverManager = MinesonAPI.getInstance()
		                                              .getServerManager();
		if (serverManager.isPaperServer() && VersionUtil.isAfterOrEqual(1190)) {
			//            return entity.isUnderWater();
			return false;
		} else {
			return entity.isInWater();
		}
	}
	
	/**
	 * Is fixed boolean.
	 *
	 * @param itemDisplay the item display
	 * @return the boolean
	 */
	public boolean isFixed(final ItemDisplay itemDisplay) {
		return itemDisplay.getItemDisplayTransform() == ItemDisplay.ItemDisplayTransform.FIXED;
	}
	
	/**
	 * Is none boolean?
	 *
	 * @param itemDisplay the item display
	 * @return the boolean
	 */
	public boolean isNone(final ItemDisplay itemDisplay) {
		return itemDisplay.getItemDisplayTransform() == ItemDisplay.ItemDisplayTransform.NONE;
	}
	
	/**
	 * Teleport.
	 *
	 * @param location the location
	 * @param entity   the entity
	 * @param cause    the cause
	 */
	public void teleport(final Location location, final Entity entity, final PlayerTeleportEvent.TeleportCause cause) {
		final ServerManager serverManager = MinesonAPI.getInstance().getServerManager();
		if (serverManager.isPaperServer() || serverManager.isFoliaServer() && VersionUtil.isAfterOrEqual(1194)) {
			//            entity.teleportAsync(location, cause);
			entity.teleport(location);
		} else {
			entity.teleport(location);
		}
	}
	
	/**
	 * Teleport.
	 *
	 * @param location the location
	 * @param entity   the entity
	 */
	public void teleport(final Location location, final Entity entity) {
		final ServerManager serverManager = MinesonAPI.getInstance()
		                                              .getServerManager();
		if (VersionUtil.isAfterOrEqual(1194) && (serverManager.isPaperServer() || serverManager.isFoliaServer())) {
			//            entity.teleportAsync(location);
			entity.teleport(location);
		} else {
			entity.teleport(location);
		}
	}
	
}
