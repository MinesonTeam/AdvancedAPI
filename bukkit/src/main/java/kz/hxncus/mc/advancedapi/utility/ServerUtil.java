package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

/**
 * The type Server util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public class ServerUtil {
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
