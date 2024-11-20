package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

import kz.hxncus.mc.advancedapi.bukkit.inventory.AdvancedInventory;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface OpenCloseable {
	@NonNull
	AdvancedInventory addOpenHandler(final Consumer<InventoryOpenEvent> openHandler);
	
	@NonNull
	AdvancedInventory addCloseHandler(final Consumer<InventoryCloseEvent> closeHandler);
	
	@NonNull
	AdvancedInventory setCloseFilter(final Predicate<Player> closeFilter);
	
	AdvancedInventory open(final HumanEntity humanEntity);
	
	void handleOpen(final InventoryOpenEvent event);
	
	boolean handleClose(final InventoryCloseEvent event);
	
	void onOpen(final InventoryOpenEvent event);
	
	void onClose(final InventoryCloseEvent event);
}
