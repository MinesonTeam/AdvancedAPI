package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

import kz.hxncus.mc.advancedapi.api.bukkit.inventory.marker.Markable;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@Getter
@Setter
public abstract class AbstractInventory implements IInventory, Markable {
	private Inventory inventory;
	private int size;
	private InventoryType inventoryType;
	private String title;
	private boolean marking = true;
	
	protected AbstractInventory(final Inventory inventory) {
		this.inventory = inventory;
		this.registerInventory();
	}
	
	protected AbstractInventory(final InventoryType inventoryType) {
		this(Bukkit.createInventory(null, inventoryType));
		this.setInventoryType(inventoryType);
	}
	
	protected AbstractInventory(final InventoryType inventoryType, final String title) {
		this(Bukkit.createInventory(null, inventoryType, title));
		this.setInventoryType(inventoryType);
		this.setTitle(title);
	}
	
	protected AbstractInventory(final int size) {
		this(Bukkit.createInventory(null, size));
		this.setSize(size);
	}
	
	protected AbstractInventory(final int size, final String title) {
		this(Bukkit.createInventory(null, size, title));
		this.setSize(size);
		this.setTitle(title);
	}
}
