package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

import kz.hxncus.mc.advancedapi.bukkit.inventory.AdvancedInventory;
import lombok.NonNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface InventoryHandler {
	@NonNull
	AdvancedInventory addItem(final ItemStack item, final Consumer<InventoryClickEvent> handler);
	
	@NonNull
	AdvancedInventory setItem(final int slot, final ItemStack item, final Consumer<InventoryClickEvent> handler);
	
	@NonNull
	AdvancedInventory setItems(final int slotFrom, final int slotTo, final ItemStack item, final Consumer<InventoryClickEvent> handler);
	
	@NonNull
	AdvancedInventory setItems(final ItemStack item, final Consumer<InventoryClickEvent> handler, final int @NonNull ... slots);
	
}
