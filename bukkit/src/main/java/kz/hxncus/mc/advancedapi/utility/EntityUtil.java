package kz.hxncus.mc.advancedapi.utility;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.bukkit.server.ServerService;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Class Entity util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public class EntityUtil {
	/**
	 * Get Bow from Metadatable shot Arrow
	 *
	 * @param arrow Arrow
	 * @return Bow ItemStack
	 */
	@Nullable
	public ItemStack getBowFromArrow(final Arrow arrow) {
		final List<MetadataValue> values = arrow.getMetadata("shot-from");
		final Object value = values.get(0).value();
		if (!(value instanceof ItemStack)) {
			return null;
		}
		return (ItemStack) value;
	}
	
	public double getAttribute(@NonNull LivingEntity entity, @NonNull Attribute attribute) {
		AttributeInstance instance = entity.getAttribute(attribute);
		return instance == null ? 0D : instance.getValue();
	}
	
	public double getAttributeBase(@NonNull LivingEntity entity, @NonNull Attribute attribute) {
		AttributeInstance instance = entity.getAttribute(attribute);
		return instance == null ? 0D : instance.getBaseValue();
	}
	
	/**
	 * Is underwater boolean.
	 *
	 * @param entity the entity
	 * @return the boolean
	 */
	public boolean isUnderWater(final Entity entity) {
		final ServerService serverService = AdvancedAPI.getInstance()
		                                               .getServerService();
		if (serverService.isPaperServer() && VersionUtil.isAfterOrEqual(1190)) {
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
		final ServerService serverService = AdvancedAPI.getInstance().getServerService();
		if (serverService.isPaperServer() || serverService.isFoliaServer() && VersionUtil.isAfterOrEqual(1194)) {
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
		final ServerService serverService = AdvancedAPI.getInstance()
		                                               .getServerService();
		if (VersionUtil.isAfterOrEqual(1194) && (serverService.isPaperServer() || serverService.isFoliaServer())) {
			//            entity.teleportAsync(location);
			entity.teleport(location);
		} else {
			entity.teleport(location);
		}
	}
	
}
