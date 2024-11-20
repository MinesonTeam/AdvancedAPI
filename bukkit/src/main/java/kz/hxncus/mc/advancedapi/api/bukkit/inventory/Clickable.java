package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

import kz.hxncus.mc.advancedapi.bukkit.inventory.AdvancedInventory;
import lombok.NonNull;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public interface Clickable {
	@NonNull
	AdvancedInventory addClickHandler(final Consumer<InventoryClickEvent> clickHandler);
	void handleClick(final InventoryClickEvent event);
	void onClick(final InventoryClickEvent event);
}
