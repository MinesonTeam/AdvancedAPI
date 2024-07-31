package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.player.PlayerTeleportEvent;

@UtilityClass
public class EntityUtil {
    public static boolean isUnderWater(Entity entity) {
        if (Versions.isPaperServer() && Versions.afterOrEqual(19)) {
//            return entity.isUnderWater();
            return false;
        } else {
            return entity.isInWater();
        }
    }

    public static boolean isFixed(ItemDisplay itemDisplay) {
        return itemDisplay.getItemDisplayTransform() == ItemDisplay.ItemDisplayTransform.FIXED;
    }

    public static boolean isNone(ItemDisplay itemDisplay) {
        return itemDisplay.getItemDisplayTransform() == ItemDisplay.ItemDisplayTransform.NONE;
    }

    public void teleport(Location location, Entity entity, PlayerTeleportEvent.TeleportCause cause) {
        if (Versions.isPaperServer() || Versions.isFoliaServer() && Versions.afterOrEqual(194)) {
//            entity.teleportAsync(location, cause);
            entity.teleport(location);
        } else {
            entity.teleport(location);
        }
    }

    public static void teleport(Location location, Entity entity) {
        if (Versions.afterOrEqual(194) && (Versions.isPaperServer() || Versions.isFoliaServer())) {
//            entity.teleportAsync(location);
            entity.teleport(location);
        } else {
            entity.teleport(location);
        }
    }

}
