package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

import lombok.NonNull;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface Clickable {
	@NonNull
	List<Consumer<InventoryClickEvent>> getClickHandlers();

	@NonNull
	Map<Integer, Consumer<InventoryClickEvent>> getItemClickHandlers();

	default Clickable putOrRemoveClickHandler(final int slot, final Consumer<InventoryClickEvent> clickHandler) {
		if (clickHandler == null) {
			this.getItemClickHandlers().remove(slot);
			return this;
		}
		this.getItemClickHandlers().put(slot, clickHandler);
		return this;
	}

	@NonNull
	default Clickable addClickHandler(final Consumer<InventoryClickEvent> clickHandler) {
		this.getClickHandlers().add(clickHandler);
		return this;
	}

	default void handleClick(final InventoryClickEvent clickEvent) {
		this.onClick(clickEvent);
		this.getClickHandlers().forEach(click -> click.accept(clickEvent));
		final Consumer<InventoryClickEvent> clickConsumer = this.getItemClickHandlers().get(clickEvent.getRawSlot());
		if (clickConsumer != null) {
			clickConsumer.accept(clickEvent);
		}
	}

	default void onClick(final InventoryClickEvent event) {
	}
}
