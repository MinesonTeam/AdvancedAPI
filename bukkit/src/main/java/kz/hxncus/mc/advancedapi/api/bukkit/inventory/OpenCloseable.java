package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface OpenCloseable {
	@NonNull
	Inventory getInventory();

	@NonNull
	List<Consumer<InventoryOpenEvent>> getOpenHandlers();

	@NonNull
	List<Consumer<InventoryCloseEvent>> getCloseHandlers();

	@NonNull
	default OpenCloseable addOpenHandler(final Consumer<InventoryOpenEvent> openHandler) {
		this.getOpenHandlers().add(openHandler);
		return this;
	}
	
	@NonNull
	default OpenCloseable addCloseHandler(final Consumer<InventoryCloseEvent> closeHandler) {
		this.getCloseHandlers().add(closeHandler);
		return this;
	}
	
	@NonNull
	Predicate<Player> getCloseFilter();
	
	@NonNull
	OpenCloseable setCloseFilter(final Predicate<Player> closeFilter);
	
	default OpenCloseable open(final HumanEntity humanEntity) {
		humanEntity.openInventory(this.getInventory());
		return this;
	}
	
	default void handleOpen(final InventoryOpenEvent openEvent) {
		this.onOpen(openEvent);
		this.getOpenHandlers().forEach(open -> open.accept(openEvent));
	}
	
	default boolean handleClose(final InventoryCloseEvent closeEvent) {
		this.onClose(closeEvent);
		this.getCloseHandlers().forEach(close -> close.accept( closeEvent));
		return this.getCloseFilter() != null && this.getCloseFilter().test((Player) closeEvent.getPlayer());
	}
	
	default void onOpen(final InventoryOpenEvent event) {
	}
	
	default void onClose(final InventoryCloseEvent event) {
	}
}
