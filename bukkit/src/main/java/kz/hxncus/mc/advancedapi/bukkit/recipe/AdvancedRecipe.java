package kz.hxncus.mc.advancedapi.bukkit.recipe;

import kz.hxncus.mc.advancedapi.api.bukkit.recipe.RecipeType;
import kz.hxncus.mc.advancedapi.utility.NamespacedKeyUtil;
import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.List;
import java.util.Map;

@Builder(toBuilder = true)
public class AdvancedRecipe {
	private RecipeType recipeType;
	private ItemStack result;
	private Material source;
	private float experience;
	private int recipeTime;
	private int uses;
	private int maxUses;
	private boolean experienceReward;
	private int villagerExperience;
	private float priceMultiplier;
	private int demand;
	private int specialPrice;
	private boolean ignoreDiscounts;
	@Builder.Default
	private final String[] shape = new String[] {"ABC", "DEF", "GHI"};
	private final Map<Character, RecipeChoice> shapedIngredients;
	private final List<RecipeChoice> shapelessIngredients;
	private RecipeChoice template;
	private RecipeChoice base;
	private RecipeChoice addition;
	
	public boolean create(String key) {
		NamespacedKey namespacedKey = NamespacedKeyUtil.create(key);
		return this.create(namespacedKey);
	}
	
	public boolean create(NamespacedKey namespacedKey) {
		if (Bukkit.getRecipe(namespacedKey) != null) {
			return false;
		}
		Recipe recipe = createRecipe(namespacedKey);
		return Bukkit.addRecipe(recipe);
	}
	
	private Recipe createRecipe(NamespacedKey namespacedKey) {
		 return switch (this.recipeType) {
			case ANVIL -> new AnvilRecipe(namespacedKey, this.result, this.base, this.addition);
			case BLASTING -> new BlastingRecipe(namespacedKey, this.result, source, experience, recipeTime);
//			case BREWERY -> new BreweryRecipe(namespacedKey, this.result);
//			case CAMPFIRE -> new CampfireRecipe(namespacedKey, this.result, source, experience, recipeTime);
//			case ENCHANT -> new EnchantRecipe(namespacedKey, this.result);
//			case GRINDSTONE -> new GrindstoneRecipe(namespacedKey, this.result);
//			case MERCHANT -> new MerchantRecipe(this.result, uses, maxUses, experienceReward, villagerExperience, priceMultiplier, demand, specialPrice, ignoreDiscounts);
			case SHAPED -> createShapedRecipe(namespacedKey);
			case SHAPELESS -> createShapelessRecipe(namespacedKey);
			case SMELTING, FURNACE -> new FurnaceRecipe(namespacedKey, this.result, source, experience, recipeTime);
			case SMITHING_TRIM -> new SmithingTrimRecipe(namespacedKey, template, base, addition);
			case SMITHING_TRANSFORM -> new SmithingTransformRecipe(namespacedKey, this.result, template, base, addition);
			case SMOKING -> new SmokingRecipe(namespacedKey, this.result, source, experience, recipeTime);
			case STONECUTTING -> new StonecuttingRecipe(namespacedKey, this.result, source);
			default -> null;
		};
	}
	
	private ShapedRecipe createShapedRecipe(NamespacedKey namespacedKey) {
		ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, this.result);
		shapedRecipe.shape(this.shape);
		this.shapedIngredients.forEach(shapedRecipe::setIngredient);
		return shapedRecipe;
	}
	
	private ShapelessRecipe createShapelessRecipe(NamespacedKey namespacedKey) {
		ShapelessRecipe shapelessRecipe = new ShapelessRecipe(namespacedKey, this.result);
		this.shapelessIngredients.forEach(shapelessRecipe::addIngredient);
		return shapelessRecipe;
	}
}
