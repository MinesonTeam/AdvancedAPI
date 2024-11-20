package kz.hxncus.mc.advancedapi.bukkit.recipe;

import lombok.Getter;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

public record AnvilRecipe(@Getter NamespacedKey key, @Getter ItemStack result, RecipeChoice base, RecipeChoice addition) implements Recipe, Keyed {
}
