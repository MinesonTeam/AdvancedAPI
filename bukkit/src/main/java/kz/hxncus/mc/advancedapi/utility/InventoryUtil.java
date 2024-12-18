package kz.hxncus.mc.advancedapi.utility;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@UtilityClass
public final class InventoryUtil {
	@NonNull
	public Inventory createInventory(final InventoryHolder inventoryHolder, final InventoryType inventoryType, int size, String title) {
		if (inventoryType != null && size == 0 && title == null) {
			return Bukkit.createInventory(inventoryHolder, inventoryType);
		} else if (inventoryType != null && title != null && size == 0) {
			return Bukkit.createInventory(inventoryHolder, inventoryType, title);
		} else if (inventoryType == null && title != null && size != 0) {
			return Bukkit.createInventory(inventoryHolder, size, title);
		} else {
			return Bukkit.createInventory(inventoryHolder, size % 9 == 0 ? size : 9);
		}
	}
}