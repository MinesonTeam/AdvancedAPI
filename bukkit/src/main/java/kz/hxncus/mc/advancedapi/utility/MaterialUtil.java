package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;

@UtilityClass
public class MaterialUtil {
	public boolean isLog(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isLog">
			case OAK_LOG:
			case SPRUCE_LOG:
			case ACACIA_LOG:
			case BIRCH_LOG:
			case CHERRY_LOG:
			case JUNGLE_LOG:
			case DARK_OAK_LOG:
			case MANGROVE_LOG:
			case LEGACY_LOG:
			case LEGACY_LOG_2:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isSword(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isSword">
			case LEGACY_WOOD_SWORD:
			case LEGACY_STONE_SWORD:
			case LEGACY_IRON_SWORD:
			case LEGACY_GOLD_SWORD:
			case LEGACY_DIAMOND_SWORD:
			case WOODEN_SWORD:
			case STONE_SWORD:
			case IRON_SWORD:
			case GOLDEN_SWORD:
			case DIAMOND_SWORD:
			case NETHERITE_SWORD:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isPickaxe(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isPickaxe">
			case LEGACY_WOOD_PICKAXE:
			case LEGACY_STONE_PICKAXE:
			case LEGACY_IRON_PICKAXE:
			case LEGACY_GOLD_PICKAXE:
			case LEGACY_DIAMOND_PICKAXE:
			case WOODEN_PICKAXE:
			case STONE_PICKAXE:
			case IRON_PICKAXE:
			case GOLDEN_PICKAXE:
			case DIAMOND_PICKAXE:
			case NETHERITE_PICKAXE:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isShovel(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isShovel">
			case LEGACY_WOOD_SPADE:
			case LEGACY_STONE_SPADE:
			case LEGACY_IRON_SPADE:
			case LEGACY_GOLD_SPADE:
			case LEGACY_DIAMOND_SPADE:
			case WOODEN_SHOVEL:
			case STONE_SHOVEL:
			case IRON_SHOVEL:
			case GOLDEN_SHOVEL:
			case DIAMOND_SHOVEL:
			case NETHERITE_SHOVEL:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isAxe(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isAxe">
			case LEGACY_WOOD_AXE:
			case LEGACY_STONE_AXE:
			case LEGACY_IRON_AXE:
			case LEGACY_GOLD_AXE:
			case LEGACY_DIAMOND_AXE:
			case WOODEN_AXE:
			case STONE_AXE:
			case IRON_AXE:
			case GOLDEN_AXE:
			case DIAMOND_AXE:
			case NETHERITE_AXE:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isHoe(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isHoe">
			case LEGACY_WOOD_HOE:
			case LEGACY_STONE_HOE:
			case LEGACY_IRON_HOE:
			case LEGACY_GOLD_HOE:
			case LEGACY_DIAMOND_HOE:
			case WOODEN_HOE:
			case STONE_HOE:
			case IRON_HOE:
			case GOLDEN_HOE:
			case DIAMOND_HOE:
			case NETHERITE_HOE:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isPotion(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isPotion">
			case LEGACY_POTION:
			case LEGACY_SPLASH_POTION:
			case LEGACY_LINGERING_POTION:
			case POTION:
			case SPLASH_POTION:
			case LINGERING_POTION:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isSpawnEgg(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isSpawnEgg">
			case ALLAY_SPAWN_EGG:
			case AXOLOTL_SPAWN_EGG:
			case BAT_SPAWN_EGG:
			case BEE_SPAWN_EGG:
			case CAT_SPAWN_EGG:
			case CHICKEN_SPAWN_EGG:
			case COD_SPAWN_EGG:
			case COW_SPAWN_EGG:
			case CREEPER_SPAWN_EGG:
			case DOLPHIN_SPAWN_EGG:
			case DONKEY_SPAWN_EGG:
			case DROWNED_SPAWN_EGG:
			case ELDER_GUARDIAN_SPAWN_EGG:
			case BLAZE_SPAWN_EGG:
			case ENDERMAN_SPAWN_EGG:
			case ENDERMITE_SPAWN_EGG:
			case ENDER_DRAGON_SPAWN_EGG:
			case EVOKER_SPAWN_EGG:
			case GHAST_SPAWN_EGG:
			case GUARDIAN_SPAWN_EGG:
			case HOGLIN_SPAWN_EGG:
			case HUSK_SPAWN_EGG:
			case IRON_GOLEM_SPAWN_EGG:
			case MAGMA_CUBE_SPAWN_EGG:
			case MOOSHROOM_SPAWN_EGG:
			case OCELOT_SPAWN_EGG:
			case PANDA_SPAWN_EGG:
			case PHANTOM_SPAWN_EGG:
			case PIG_SPAWN_EGG:
			case POLAR_BEAR_SPAWN_EGG:
			case PUFFERFISH_SPAWN_EGG:
			case RABBIT_SPAWN_EGG:
			case SHEEP_SPAWN_EGG:
			case SILVERFISH_SPAWN_EGG:
			case SKELETON_SPAWN_EGG:
			case SLIME_SPAWN_EGG:
			case SPIDER_SPAWN_EGG:
			case SQUID_SPAWN_EGG:
			case STRAY_SPAWN_EGG:
			case TRADER_LLAMA_SPAWN_EGG:
			case BREEZE_SPAWN_EGG:
			case WANDERING_TRADER_SPAWN_EGG:
			case WITCH_SPAWN_EGG:
			case WITHER_SKELETON_SPAWN_EGG:
			case ZOGLIN_SPAWN_EGG:
			case ZOMBIE_SPAWN_EGG:
			case ZOMBIE_VILLAGER_SPAWN_EGG:
			case ZOMBIFIED_PIGLIN_SPAWN_EGG:
			case CAMEL_SPAWN_EGG:
			case LLAMA_SPAWN_EGG:
			case MULE_SPAWN_EGG:
			case PARROT_SPAWN_EGG:
			case PIGLIN_SPAWN_EGG:
			case PIGLIN_BRUTE_SPAWN_EGG:
			case PILLAGER_SPAWN_EGG:
			case FOX_SPAWN_EGG:
			case RAVAGER_SPAWN_EGG:
			case VEX_SPAWN_EGG:
			case VINDICATOR_SPAWN_EGG:
			case FROG_SPAWN_EGG:
			case GOAT_SPAWN_EGG:
			case SHULKER_SPAWN_EGG:
			case TURTLE_SPAWN_EGG:
			case ZOMBIE_HORSE_SPAWN_EGG:
			case HORSE_SPAWN_EGG:
			case SKELETON_HORSE_SPAWN_EGG:
			case SALMON_SPAWN_EGG:
			case SNIFFER_SPAWN_EGG:
			case TROPICAL_FISH_SPAWN_EGG:
			case CAVE_SPIDER_SPAWN_EGG:
			case STRIDER_SPAWN_EGG:
			case TADPOLE_SPAWN_EGG:
			case VILLAGER_SPAWN_EGG:
			case WARDEN_SPAWN_EGG:
			case WITHER_SPAWN_EGG:
			case WOLF_SPAWN_EGG:
			case GLOW_SQUID_SPAWN_EGG:
			case SNOW_GOLEM_SPAWN_EGG:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isHead(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isHead">
			case DRAGON_HEAD:
			case PIGLIN_HEAD:
			case ZOMBIE_HEAD:
			case CREEPER_HEAD:
			case PLAYER_HEAD:
			case PLAYER_WALL_HEAD:
			case CREEPER_WALL_HEAD:
			case ZOMBIE_WALL_HEAD:
			case PIGLIN_WALL_HEAD:
			case DRAGON_WALL_HEAD:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isMinecart(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isMinecart">
			case CHEST_MINECART:
			case COMMAND_BLOCK_MINECART:
			case FURNACE_MINECART:
			case HOPPER_MINECART:
			case TNT_MINECART:
			case MINECART:
			case LEGACY_MINECART:
			case LEGACY_COMMAND_MINECART:
			case LEGACY_EXPLOSIVE_MINECART:
			case LEGACY_HOPPER_MINECART:
			case LEGACY_POWERED_MINECART:
			case LEGACY_STORAGE_MINECART:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isBoat(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isBoat">
			case ACACIA_BOAT:
			case BIRCH_BOAT:
			case DARK_OAK_BOAT:
			case JUNGLE_BOAT:
			case OAK_BOAT:
			case SPRUCE_BOAT:
			case BIRCH_CHEST_BOAT:
			case ACACIA_CHEST_BOAT:
			case DARK_OAK_CHEST_BOAT:
			case JUNGLE_CHEST_BOAT:
			case OAK_CHEST_BOAT:
			case SPRUCE_CHEST_BOAT:
			case CHERRY_BOAT:
			case CHERRY_CHEST_BOAT:
			case MANGROVE_BOAT:
			case MANGROVE_CHEST_BOAT:
			case LEGACY_BOAT:
			case LEGACY_BOAT_ACACIA:
			case LEGACY_BOAT_BIRCH:
			case LEGACY_BOAT_DARK_OAK:
			case LEGACY_BOAT_JUNGLE:
			case LEGACY_BOAT_SPRUCE:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isBanner(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isBanner">
			case BLACK_BANNER:
			case BLUE_BANNER:
			case BROWN_BANNER:
			case CYAN_BANNER:
			case GRAY_BANNER:
			case GREEN_BANNER:
			case LIGHT_BLUE_BANNER:
			case LIGHT_GRAY_BANNER:
			case BLACK_WALL_BANNER:
			case BLUE_WALL_BANNER:
			case BROWN_WALL_BANNER:
			case CYAN_WALL_BANNER:
			case GRAY_WALL_BANNER:
			case GREEN_WALL_BANNER:
			case LIGHT_BLUE_WALL_BANNER:
			case LIME_BANNER:
			case LIGHT_GRAY_WALL_BANNER:
			case LIME_WALL_BANNER:
			case MAGENTA_BANNER:
			case MAGENTA_WALL_BANNER:
			case ORANGE_BANNER:
			case PINK_BANNER:
			case PURPLE_BANNER:
			case RED_BANNER:
			case WHITE_BANNER:
			case YELLOW_BANNER:
			case PINK_WALL_BANNER:
			case PURPLE_WALL_BANNER:
			case RED_WALL_BANNER:
			case WHITE_WALL_BANNER:
			case YELLOW_WALL_BANNER:
			case ORANGE_WALL_BANNER:
			case LEGACY_BANNER:
			case LEGACY_STANDING_BANNER:
			case LEGACY_WALL_BANNER:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isBed(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isBed">
			case BROWN_BED:
			case BLUE_BED:
			case BLACK_BED:
			case GREEN_BED:
			case LIGHT_BLUE_BED:
			case LIGHT_GRAY_BED:
			case LIME_BED:
			case MAGENTA_BED:
			case ORANGE_BED:
			case PINK_BED:
			case PURPLE_BED:
			case RED_BED:
			case WHITE_BED:
			case YELLOW_BED:
			case CYAN_BED:
			case GRAY_BED:
			case LEGACY_BED:
			case LEGACY_BED_BLOCK:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isRail(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isRail">
			case RAIL:
			case POWERED_RAIL:
			case ACTIVATOR_RAIL:
			case DETECTOR_RAIL:
			case LEGACY_DETECTOR_RAIL:
			case LEGACY_POWERED_RAIL:
			case LEGACY_ACTIVATOR_RAIL:
			case LEGACY_RAILS:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isBucket(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isBucket">
			case WATER_BUCKET:
			case LAVA_BUCKET:
			case BUCKET:
			case AXOLOTL_BUCKET:
			case COD_BUCKET:
			case PUFFERFISH_BUCKET:
			case SALMON_BUCKET:
			case TROPICAL_FISH_BUCKET:
			case MILK_BUCKET:
			case TADPOLE_BUCKET:
			case POWDER_SNOW_BUCKET:
			case LEGACY_BUCKET:
			case LEGACY_LAVA_BUCKET:
			case LEGACY_MILK_BUCKET:
			case LEGACY_WATER_BUCKET:
				return true;
			default:
					return false;
		}
	}
	
	public boolean isShulker(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isShulker">
			case BLACK_SHULKER_BOX:
			case BLUE_SHULKER_BOX:
			case BROWN_SHULKER_BOX:
			case CYAN_SHULKER_BOX:
			case GRAY_SHULKER_BOX:
			case GREEN_SHULKER_BOX:
			case SHULKER_BOX:
			case LIME_SHULKER_BOX:
			case LIGHT_GRAY_SHULKER_BOX:
			case MAGENTA_SHULKER_BOX:
			case ORANGE_SHULKER_BOX:
			case PINK_SHULKER_BOX:
			case PURPLE_SHULKER_BOX:
			case RED_SHULKER_BOX:
			case WHITE_SHULKER_BOX:
			case YELLOW_SHULKER_BOX:
			case LIGHT_BLUE_SHULKER_BOX:
			case LEGACY_BLACK_SHULKER_BOX:
			case LEGACY_BLUE_SHULKER_BOX:
			case LEGACY_BROWN_SHULKER_BOX:
			case LEGACY_CYAN_SHULKER_BOX:
			case LEGACY_GRAY_SHULKER_BOX:
			case LEGACY_GREEN_SHULKER_BOX:
			case LEGACY_LIGHT_BLUE_SHULKER_BOX:
			case LEGACY_LIME_SHULKER_BOX:
			case LEGACY_MAGENTA_SHULKER_BOX:
			case LEGACY_ORANGE_SHULKER_BOX:
			case LEGACY_PINK_SHULKER_BOX:
			case LEGACY_PURPLE_SHULKER_BOX:
			case LEGACY_RED_SHULKER_BOX:
			case LEGACY_WHITE_SHULKER_BOX:
			case LEGACY_YELLOW_SHULKER_BOX:
			case LEGACY_SILVER_SHULKER_BOX:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isSign(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isSign">
			case ACACIA_HANGING_SIGN:
			case OAK_SIGN:
			case SPRUCE_SIGN:
			case BIRCH_SIGN:
			case JUNGLE_SIGN:
			case ACACIA_SIGN:
			case DARK_OAK_SIGN:
			case MANGROVE_SIGN:
			case OAK_WALL_SIGN:
			case SPRUCE_WALL_SIGN:
			case BIRCH_WALL_SIGN:
			case JUNGLE_WALL_SIGN:
			case ACACIA_WALL_SIGN:
			case DARK_OAK_WALL_SIGN:
			case MANGROVE_WALL_SIGN:
			case LEGACY_SIGN:
			case LEGACY_WALL_SIGN:
			case BAMBOO_SIGN:
			case ACACIA_WALL_HANGING_SIGN:
			case DARK_OAK_WALL_HANGING_SIGN:
			case MANGROVE_WALL_HANGING_SIGN:
			case BAMBOO_HANGING_SIGN:
			case CHERRY_SIGN:
			case CRIMSON_SIGN:
			case WARPED_SIGN:
			case LEGACY_SIGN_POST:
			case BAMBOO_WALL_HANGING_SIGN:
			case CHERRY_WALL_SIGN:
			case CRIMSON_WALL_SIGN:
			case WARPED_WALL_SIGN:
			case WARPED_WALL_HANGING_SIGN:
			case BAMBOO_WALL_SIGN:
			case CHERRY_WALL_HANGING_SIGN:
			case CRIMSON_WALL_HANGING_SIGN:
			case BIRCH_HANGING_SIGN:
			case BIRCH_WALL_HANGING_SIGN:
			case CRIMSON_HANGING_SIGN:
			case JUNGLE_HANGING_SIGN:
			case JUNGLE_WALL_HANGING_SIGN:
			case OAK_HANGING_SIGN:
			case OAK_WALL_HANGING_SIGN:
			case WARPED_HANGING_SIGN:
			case CHERRY_HANGING_SIGN:
			case DARK_OAK_HANGING_SIGN:
			case MANGROVE_HANGING_SIGN:
			case SPRUCE_HANGING_SIGN:
			case SPRUCE_WALL_HANGING_SIGN:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isArrow(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isArrow">
			case ARROW:
			case TIPPED_ARROW:
			case LEGACY_ARROW:
			case SPECTRAL_ARROW:
			case LEGACY_TIPPED_ARROW:
			case LEGACY_SPECTRAL_ARROW:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isHorseArmor(Material material) {
		switch (material) {
			//<editor-fold defaultstate="collapsed" desc="isHorseArmor">
			case LEATHER_HORSE_ARMOR:
			case IRON_HORSE_ARMOR:
			case GOLDEN_HORSE_ARMOR:
			case DIAMOND_HORSE_ARMOR:
				return true;
			default:
				return false;
		}
	}
}
