package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.SimplePluginManager;

/**
 * The type Server util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public final class ServerUtil {
	public SimplePluginManager getSimplePluginManager() {
		return (SimplePluginManager) Bukkit.getPluginManager();
	}

	public boolean isPaperServer() {
		if ("Paper".equalsIgnoreCase(Bukkit.getName())) {
			return true;
		}
		try {
			Class.forName("com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent");
			return true;
		} catch (final ClassNotFoundException e) {
			return false;
		}
	}
	
	public boolean isFoliaServer() {
		return "Folia".equalsIgnoreCase(Bukkit.getName());
	}

	public void removeRecipes(String... recipes) {
		for (String recipe : recipes) {
			ServerUtil.removeRecipe(recipe);
		}
	}
	
	public void removeRecipe(String recipe) {
		NamespacedKey key = NamespacedKeyUtil.create(recipe);
		Bukkit.removeRecipe(key);
	}
}
