package kz.hxncus.mc.advancedapi.utility;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.annotation.Inject;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.Locale;

/**
 * The type Namespaced key util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public final class NamespacedKeyUtil {
	@Inject
	private AdvancedAPI plugin;

	/**
	 * Create a namespaced key.
	 *
	 * @param key the key
	 * @return the namespaced key
	 */
	public NamespacedKey create(final String key) {
		return new NamespacedKey(plugin, key);
	}

	public boolean isOwnedKey(Plugin plugin, NamespacedKey key) {
		if (key == null) {
			return false;
		}
		return key.getNamespace().equals(plugin.getName().toLowerCase(Locale.ROOT));
	}

	/**
	 * Check if key belongs to the main plugin
	 *
	 * @param key the namespaced key
	 * @return true if key belongs to main plugin
	 */
	public boolean isOwnedKey(NamespacedKey key) {
		if (key == null) {
			return false;
		}
		return isOwnedKey(plugin, key);
	}
}