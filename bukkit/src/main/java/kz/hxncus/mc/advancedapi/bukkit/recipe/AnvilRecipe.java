package kz.hxncus.mc.advancedapi.bukkit.recipe;

import lombok.Data;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

@Data
public class AnvilRecipe implements Recipe, Keyed {
    private final NamespacedKey key;
    private final ItemStack result;
    private final RecipeChoice base;
    private final RecipeChoice addition;
}
