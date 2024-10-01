package kz.hxncus.mc.minesonapi.utility;

import kz.hxncus.mc.minesonapi.MinesonAPIPlugin;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;

/**
 * The type Namespaced key util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public class NamespacedKeyUtil {
	/**
	 * Create a namespaced key.
	 *
	 * @param key the key
	 * @return the namespaced key
	 */
	public NamespacedKey create(final String key) {
		return new NamespacedKey(MinesonAPIPlugin.getInstance(), key);
	}
}