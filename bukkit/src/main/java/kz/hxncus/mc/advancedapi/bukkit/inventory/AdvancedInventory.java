package kz.hxncus.mc.advancedapi.bukkit.inventory;

import kz.hxncus.mc.advancedapi.annotation.Inject;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.AbstractInventory;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.Clickable;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.InventoryHandler;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.OpenCloseable;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.marker.ItemMarker;
import kz.hxncus.mc.advancedapi.utility.InventoryUtil;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter
public class AdvancedInventory extends AbstractInventory implements InventoryHandler, OpenCloseable, Clickable {
	@Inject
	private InventoryController controller;
	private final Map<Integer, Consumer<InventoryClickEvent>> itemClickHandlers = new HashMap<>();
	private final List<Consumer<InventoryOpenEvent>> openHandlers = new ArrayList<>();
	private final List<Consumer<InventoryCloseEvent>> closeHandlers = new ArrayList<>();
	private final List<Consumer<InventoryClickEvent>> clickHandlers = new ArrayList<>();
	private Predicate<Player> closeFilter;

	public AdvancedInventory(final Inventory inventory) {
		super(inventory);
	}
	
	public AdvancedInventory(final InventoryType inventoryType) {
		super(inventoryType);
	}
	
	public AdvancedInventory(final InventoryType inventoryType, final String title) {
		super(inventoryType, title);
	}
	
	public AdvancedInventory(final int size) {
		super(size);
	}
	
	public AdvancedInventory(final int size, final String title) {
		super(size, title);
	}

	public AdvancedInventory(@NonNull final AdvancedInventory advancedInventory) {
		super(InventoryUtil.createInventory(null, advancedInventory.getInventoryType(), advancedInventory.getSize(), advancedInventory.getTitle()));
		this.getInventory().setContents(advancedInventory.getContents());
		
		this.setInventoryType(advancedInventory.getInventoryType());
		this.setSize(advancedInventory.getSize());
		this.setTitle(advancedInventory.getTitle());
		
		this.itemClickHandlers.putAll(advancedInventory.itemClickHandlers);
		this.openHandlers.addAll(advancedInventory.openHandlers);
		this.closeHandlers.addAll(advancedInventory.closeHandlers);
		this.clickHandlers.addAll(advancedInventory.clickHandlers);
		this.closeFilter = advancedInventory.closeFilter;
		this.setMarking(advancedInventory.isMarking());
	}
	
	@Override
	public void registerInventory() {
		controller.registerInventory(this);
	}
	
	@NonNull
	public AdvancedInventory putOrRemoveClickHandler(final int slot, final Consumer<InventoryClickEvent> clickHandler) {
		Clickable.super.putOrRemoveClickHandler(slot, clickHandler);
		return this;
	}

	@Override
	@NonNull
	public AdvancedInventory setItem(final int slot, final ItemStack item, final Consumer<InventoryClickEvent> clickHandler) {
		final ItemMarker itemMarker = controller.getItemMarker();
		super.setItem(slot, this.isMarking() ? itemMarker.markItem(item) : item);
		this.putOrRemoveClickHandler(slot, clickHandler);
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory updateTitle(@NonNull final String title) {
		super.updateTitle(title);
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory addItem(final ItemStack item) {
		return this.addItem(item, null);
	}
	
	@Override
	@NonNull
	public AdvancedInventory addItem(final ItemStack item, final Consumer<InventoryClickEvent> handler) {
		final int slot = this.getInventory().firstEmpty();
		if (slot != -1) {
			this.setItem(slot, item, handler);
		}
		return this;
	}

	@Override
	@NonNull
	public AdvancedInventory setItem(final int slot, final ItemStack item) {
		return this.setItem(slot, item, null);
	}
	
	@Override
	@NonNull
	public AdvancedInventory setItems(final int slotFrom, final int slotTo, final ItemStack item) {
		return this.setItems(slotFrom, slotTo, item, null);
	}
	
	@Override
	@NonNull
	public AdvancedInventory setItems(final int slotFrom, final int slotTo, final ItemStack item, final Consumer<InventoryClickEvent> handler) {
		for (int i = slotFrom; i <= slotTo; i++) {
			this.setItem(i, item, handler);
		}
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory setItems(final ItemStack item, final int @NonNull ... slots) {
		return this.setItems(item, null, slots);
	}
	
	@Override
	@NonNull
	public AdvancedInventory setItems(final ItemStack item, final Consumer<InventoryClickEvent> handler, final int @NonNull ... slots) {
		for (final int slot : slots) {
			this.setItem(slot, item, handler);
		}
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory removeItem(final int slot) {
		super.removeItem(slot);
		this.getItemClickHandlers().remove(slot);
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory removeItems(final int @NonNull ... slots) {
		for (final int slot : slots) {
			this.removeItem(slot);
		}
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory removeItems(final int slotFrom, final int slotTo) {
		for (int i = slotFrom; i <= slotTo; i++) {
			this.removeItem(i);
		}
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory clear() {
		super.clear();
		this.getItemClickHandlers().clear();
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory addOpenHandler(final Consumer<InventoryOpenEvent> openHandler) {
		OpenCloseable.super.addOpenHandler(openHandler);
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory addCloseHandler(final Consumer<InventoryCloseEvent> closeHandler) {
		OpenCloseable.super.addCloseHandler(closeHandler);
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory addClickHandler(final Consumer<InventoryClickEvent> clickHandler) {
		Clickable.super.addClickHandler(clickHandler);
		return this;
	}
	
	@Override
	@NonNull
	public AdvancedInventory setCloseFilter(final Predicate<Player> closeFilter) {
		this.closeFilter = closeFilter;
		return this;
	}
	
	@Override
	public AdvancedInventory open(final HumanEntity humanEntity) {
		OpenCloseable.super.open(humanEntity);
		return this;
	}
	
	@NonNull
	public AdvancedInventory copy() {
		return new AdvancedInventory(this);
	}
}
