package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

import lombok.NonNull;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IInventory {
	@NonNull
	Inventory getInventory();
	
	void setInventory(Inventory inventory);
	
	@NonNull
	IInventory updateTitle(@NonNull final String title);
	
	@NonNull
	IInventory addItem(final ItemStack item);
	
	ItemStack @NonNull [] getContents();
	
	int getSize();
	
	ItemStack @NonNull [] getItems(final int slotFrom, final int slotTo);
	
	ItemStack @NonNull [] getItems(final int @NonNull ... slots);
	
	ItemStack getItem(final int slot);
	
	@NonNull
	IInventory setItem(final int slot, final ItemStack item);
	
	@NonNull
	IInventory setItems(final int slotFrom, final int slotTo, final ItemStack item);
	
	@NonNull
	IInventory setItems(final ItemStack item, final int @NonNull ... slots);
	
	@NonNull
	IInventory removeItems(final int @NonNull ... slots);
	
	@NonNull
	IInventory removeItem(final int slot);
	
	@NonNull
	IInventory removeItems(final int slotFrom, final int slotTo);
	
	@NonNull
	IInventory clear();
}
