package kz.hxncus.mc.advancedapi.bukkit.recipe;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.api.bukkit.recipe.RecipeType;
import kz.hxncus.mc.advancedapi.utility.NamespacedKeyUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

@Getter
@Setter
public class SimpleRecipe extends CraftingRecipe {
	private AdvancedAPI plugin;
	private RecipeType recipeType;
	
	public SimpleRecipe(final AdvancedAPI plugin, final String key, final ItemStack result, final RecipeType recipeType) {
		super(NamespacedKeyUtil.create(key), result);
		this.plugin = plugin;
		this.recipeType = recipeType;
	}
	
	public SimpleRecipe(final SimpleRecipe simpleRecipe) {
		super(simpleRecipe.getKey(), simpleRecipe.getResult());
		this.plugin = simpleRecipe.plugin;
		this.recipeType = simpleRecipe.recipeType;
	}
	
	public void create() {
		if (this.recipeType == RecipeType.SHAPED) {
			final ShapedRecipe recipe = new ShapedRecipe(this.getKey(), this.getResult());
			this.plugin.getServer()
			           .addRecipe(recipe);
		} else {
			final ShapelessRecipe recipe = new ShapelessRecipe(this.getKey(), this.getResult());
			this.plugin.getServer()
			           .addRecipe(recipe);
		}
	}
}
