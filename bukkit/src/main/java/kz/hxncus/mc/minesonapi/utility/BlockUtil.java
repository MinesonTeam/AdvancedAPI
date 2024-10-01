package kz.hxncus.mc.minesonapi.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataType;

/**
 * Class Block util.
 *
 * @author Hxncus
 * @since 1.0.1
 */
@UtilityClass
public class BlockUtil {
	/**
	 * The Radix.
	 */
	public final int RADIX = 16;
	
	/**
	 * Is the player placed block?
	 *
	 * @param block the block
	 * @return the boolean
	 */
	public boolean isPlayerPlaced(final Block block) {
		final int hashCode = block.getLocation().hashCode();
		final String key = Integer.toString(hashCode, RADIX);
		final NamespacedKey namespacedKey = NamespacedKeyUtil.create(key);
		return block.getChunk().getPersistentDataContainer()
		            .has(namespacedKey, PersistentDataType.INTEGER);
	}
}