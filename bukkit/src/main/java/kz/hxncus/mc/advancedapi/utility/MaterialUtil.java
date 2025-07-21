package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;

@UtilityClass
public final class MaterialUtil {
	public boolean isLegacy(final Material type) {
		return type.name().startsWith("LEGACY_");
	}

	public boolean isWool(final Material type) {
        return type.name().endsWith("_WOOL");
    }

	public boolean isLog(Material type) {
		String name = type.name();
		return name.endsWith("_LOG") || name.endsWith("_LOG_2");
	}
	
	public boolean isLeave(Material type) {
		String name = type.name();
		return name.endsWith("_LEAVES") || name.endsWith("_LEAVES_2");
	}
	
	public boolean isSword(Material type) {
		return type.name().endsWith("_SWORD");
	}
	
	public boolean isPickaxe(Material type) {
		return type.name().endsWith("_PICKAXE");
	}
	
	public boolean isShovel(Material type) {
		String name = type.name();
		return name.endsWith("_SHOVEL") || name.endsWith("_SPADE");
	}
	
	public boolean isAxe(Material type) {
		return type.name().endsWith("_AXE");
	}
	
	public boolean isHoe(Material type) {
		return type.name().endsWith("_HOE");
	}
	
	public boolean isPotion(Material type) {
		return type.name().endsWith("_POTION");
	}
	
	public boolean isSpawnEgg(Material type) {
		return type.name().endsWith("_SPAWN_EGG");
	}
	
	public boolean isHead(Material type) {
		return type.name().endsWith("_HEAD");
	}
	
	public boolean isSkull(Material type) {
		return type.name().endsWith("_SKULL");
	}

	public boolean isMinecart(Material type) {
		return type.name().endsWith("_MINECART");
	}
	
	public boolean isBoat(Material type) {
		return type.name().endsWith("_BOAT") || type.name().contains("_BOAT_");
	}
	
	public boolean isBanner(Material type) {
		return type.name().endsWith("_BANNER");
	}
	
	public boolean isBed(Material type) {
		String name = type.name();
		return name.endsWith("_BED") || name.endsWith("_BED_BLOCK");
	}
	
	public boolean isRail(Material type) {
		String name = type.name();
		return name.endsWith("_RAIL") || name.endsWith("RAILS");
	}
	
	public boolean isBucket(Material type) {
		return type.name().endsWith("_BUCKET");
	}

	public boolean isShulker(Material type) {
		return type.name().endsWith("_SHULKER_BOX");
	}
	
	public boolean isSign(Material type) {
		return type.name().endsWith("_SIGN");
	}

	public boolean isHangingSign(Material type) {
		return type.name().endsWith("_HANGING_SIGN");
	}

	public boolean isArrow(Material type) {
		return type.name().endsWith("_ARROW");
	}
	
	public boolean isHorseArmor(Material type) {
		return type.name().endsWith("_HORSE_ARMOR");
	}
	
	public boolean isSapling(Material type) {
		return type.name().endsWith("_SAPLING");
	}
}
