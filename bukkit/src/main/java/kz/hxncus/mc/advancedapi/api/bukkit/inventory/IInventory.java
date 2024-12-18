package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

import lombok.NonNull;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IInventory {
	@NonNull
	Inventory getInventory();
	
	void setInventory(Inventory inventory);
	
	default void registerInventory() {
	}

	default IInventory updateTitle(@NonNull final String title) {
		Inventory inventory = this.getInventory();
		final List<HumanEntity> viewers = inventory.getViewers();

		Inventory updatedInventory = Bukkit.createInventory(null, inventory.getSize(), title);
		updatedInventory.setContents(inventory.getContents());
		this.setInventory(updatedInventory);

		for (final HumanEntity viewer : viewers) {
			viewer.openInventory(updatedInventory);
		}

		return this;
	}
	
	@NonNull
	default IInventory addItem(final ItemStack item) {
		this.getInventory().addItem(item);
		return this;
	}
	
	default ItemStack @NonNull [] getContents() {
		return this.getInventory().getContents();
	}
	
	default int getSize() {
		return this.getInventory().getSize();
	}
	
	default ItemStack @NonNull [] getItems(final int slotFrom, final int slotTo) {
		if (slotFrom == slotTo || slotFrom > slotTo) {
			throw new IllegalArgumentException("slotFrom must be less than slotTo");
		}
		final ItemStack[] items = new ItemStack[slotTo - slotFrom + 1];
		for (int i = slotFrom; i <= slotTo; i++) {
			items[i - slotFrom] = this.getInventory().getItem(i);
		}
		return items;
	}
	
	default ItemStack @NonNull [] getItems(final int @NonNull ... slots) {
		final ItemStack[] items = new ItemStack[slots.length];
		for (int i = 0; i < slots.length; i++) {
			items[i] = this.getItem(slots[i]);
		}
		return items;
	}
	
	default ItemStack getItem(final int slot) {
		return this.getInventory().getItem(slot);
	}
	
	@NonNull
	default IInventory setItem(final int slot, final ItemStack item) {
		this.getInventory().setItem(slot, item);
		return this;
	}
	
	@NonNull
	default IInventory setItems(final int slotFrom, final int slotTo, final ItemStack item) {
		for (int i = slotFrom; i <= slotTo; i++) {
			this.setItem(i, item);
		}
		return this;
	}
	
	@NonNull
	default IInventory setItems(final ItemStack item, final int @NonNull ... slots) {
		for (final int slot : slots) {
			this.setItem(slot, item);
		}
		return this;
	}
	
	@NonNull
	default IInventory removeItems(final int @NonNull ... slots) {
		for (final int slot : slots) {
			this.removeItem(slot);
		}
		return this;
	}
	
	@NonNull
	default IInventory removeItem(final int slot) {
		this.getInventory().clear(slot);
		return this;
	}
	
	@NonNull
	default IInventory removeItems(final int slotFrom, final int slotTo) {
		for (int i = slotFrom; i <= slotTo; i++) {
			this.removeItem(i);
		}
		return this;
	}

	@NonNull
	default IInventory clear() {
		this.getInventory().clear();
		return this;
	}
}
