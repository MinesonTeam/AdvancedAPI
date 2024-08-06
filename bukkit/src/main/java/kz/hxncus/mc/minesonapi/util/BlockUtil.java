package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataType;

@UtilityClass
public class BlockUtil {
	public final int RADIX = 16;
	
	public boolean isPlayerPlaced(final Block block) {
		final int hashCode = block.getLocation()
		                          .hashCode();
		final String key = Integer.toString(hashCode, RADIX);
		final NamespacedKey namespacedKey = NamespacedKeyUtil.create(key);
		return block.getChunk()
		            .getPersistentDataContainer()
		            .has(namespacedKey, PersistentDataType.INTEGER);
	}
}