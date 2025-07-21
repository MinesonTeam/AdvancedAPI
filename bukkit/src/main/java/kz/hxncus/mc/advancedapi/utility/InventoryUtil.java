package kz.hxncus.mc.advancedapi.utility;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@UtilityClass
public final class InventoryUtil {
	public Inventory clone(Inventory inventory, String title) {
		Inventory clone = InventoryUtil.createInventory(inventory.getHolder(), inventory.getType(), inventory.getSize(), title);
		for (int i = 0; i < inventory.getSize(); i++) {
			clone.setItem(i, inventory.getItem(i));
		}
		return clone;
	}

	@NonNull
	public Inventory createInventory(final InventoryHolder inventoryHolder, final InventoryType inventoryType, int size, String title) {
		if (inventoryType == null || inventoryType == InventoryType.CHEST) {
			if (title == null || title.isEmpty()) {
				return Bukkit.createInventory(inventoryHolder, size);
			}
			return Bukkit.createInventory(inventoryHolder, size, title);
		} else {
			if (title == null || title.isEmpty()) {
				return Bukkit.createInventory(inventoryHolder, inventoryType);
			}
			return Bukkit.createInventory(inventoryHolder, inventoryType, title);
		}
	}
}
