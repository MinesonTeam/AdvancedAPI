package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

import kz.hxncus.mc.advancedapi.api.bukkit.inventory.marker.Markable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;

public abstract class AbstractInventory implements IInventory, Markable {
	@Getter
	@Setter
	private Inventory inventory;
	@Getter
	@Setter
	private boolean marking = true;
	
	protected AbstractInventory(final Inventory inventory) {
		this.inventory = inventory;
	}
}
