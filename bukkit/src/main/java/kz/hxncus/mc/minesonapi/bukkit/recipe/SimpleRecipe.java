package kz.hxncus.mc.minesonapi.bukkit.recipe;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.util.NamespacedKeyUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

@Getter
@Setter
public class SimpleRecipe extends CraftingRecipe {
    private MinesonAPI plugin;
    private RecipeType recipeType;

    public SimpleRecipe(MinesonAPI plugin, String key, ItemStack result, RecipeType recipeType) {
        super(NamespacedKeyUtil.create(key), result);
        this.plugin = plugin;
        this.recipeType = recipeType;
    }

    public SimpleRecipe(SimpleRecipe simpleRecipe) {
        super(simpleRecipe.getKey(), simpleRecipe.getResult());
        this.plugin = simpleRecipe.getPlugin();
        this.recipeType = simpleRecipe.getRecipeType();
    }

    public void create() {
        if (recipeType == RecipeType.SHAPED) {
            ShapedRecipe recipe = new ShapedRecipe(this.getKey(), this.getResult());
            plugin.getServer().addRecipe(recipe);
        } else {
            ShapelessRecipe recipe = new ShapelessRecipe(this.getKey(), this.getResult());
            plugin.getServer().addRecipe(recipe);
        }
    }
}
