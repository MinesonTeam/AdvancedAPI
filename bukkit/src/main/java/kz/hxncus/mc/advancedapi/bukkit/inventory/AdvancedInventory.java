package kz.hxncus.mc.advancedapi.bukkit.inventory;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.AbstractInventory;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.Clickable;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.InventoryHandler;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.OpenCloseable;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.marker.ItemMarker;
import kz.hxncus.mc.advancedapi.module.ServiceModule;
import kz.hxncus.mc.advancedapi.utility.InventoryUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
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

public class AdvancedInventory extends AbstractInventory implements InventoryHandler, OpenCloseable, Clickable {
	@Getter
	@Setter
	private static InventoryService service;
	private int size;
	private InventoryType inventoryType;
	private String title;
	private final Map<Integer, Consumer<InventoryClickEvent>> itemClickHandlers = new HashMap<>();
	private final List<Consumer<InventoryOpenEvent>> openHandlers = new ArrayList<>();
	private final List<Consumer<InventoryCloseEvent>> closeHandlers = new ArrayList<>();
	private final List<Consumer<InventoryClickEvent>> clickHandlers = new ArrayList<>();
	private Predicate<Player> closeFilter;
	
	public AdvancedInventory(@NonNull final Inventory inventory) {
		super(inventory);
		this.registerInventory();
	}
	
	public AdvancedInventory(final InventoryType inventoryType) {
		this(Bukkit.createInventory(null, inventoryType));
		this.inventoryType = inventoryType;
	}
	
	public AdvancedInventory(final InventoryType inventoryType, final String title) {
		this(Bukkit.createInventory(null, inventoryType, title));
		this.inventoryType = inventoryType;
		this.title = title;
	}
	
	public AdvancedInventory(final int size) {
		this(Bukkit.createInventory(null, size));
		this.size = size;
	}
	
	public AdvancedInventory(final int size, final String title) {
		this(Bukkit.createInventory(null, size, title));
		this.size = size;
		this.title = title;
	}
	
	public AdvancedInventory(@NonNull final AdvancedInventory advancedInventory) {
		this(InventoryUtil.createInventory(null, advancedInventory.inventoryType, advancedInventory.size, advancedInventory.title));
		this.getInventory().setContents(advancedInventory.getContents());
		
		this.inventoryType = advancedInventory.inventoryType;
		this.size = advancedInventory.size;
		this.title = advancedInventory.title;
		
		this.itemClickHandlers.putAll(advancedInventory.itemClickHandlers);
		this.openHandlers.addAll(advancedInventory.openHandlers);
		this.closeHandlers.addAll(advancedInventory.closeHandlers);
		this.clickHandlers.addAll(advancedInventory.clickHandlers);
		this.closeFilter = advancedInventory.closeFilter;
		this.setMarking(advancedInventory.isMarking());
	}
	
	private void registerInventory() {
		final AdvancedAPI plugin = AdvancedAPI.getInstance();
		ServiceModule serviceModule = plugin.getServiceModule();
		
		AdvancedInventory.service = serviceModule.getService(InventoryService.class);
		AdvancedInventory.service.registerInventory(this);
	}
	
	@Override
	@NonNull
	public AdvancedInventory updateTitle(@NonNull final String title) {
		Inventory inventory = this.getInventory();
		final List<HumanEntity> viewers = inventory.getViewers();
		Inventory updatedInventory = Bukkit.createInventory(null, inventory.getSize(), title);
		updatedInventory.setContents(inventory.getContents());
		setInventory(updatedInventory);
		for (final HumanEntity viewer : viewers) {
			viewer.openInventory(updatedInventory);
		}
		return this;
	}
	
	@NonNull
	public AdvancedInventory addItem(final ItemStack item) {
		return this.addItem(item, null);
	}
	
	@NonNull
	public AdvancedInventory addItem(final ItemStack item, final Consumer<InventoryClickEvent> handler) {
		final int slot = this.getInventory().firstEmpty();
		if (slot != -1) {
			this.setItem(slot, item, handler);
		}
		return this;
	}
	
	@NonNull
	public AdvancedInventory setItem(final int slot, final ItemStack item, final Consumer<InventoryClickEvent> clickHandler) {
		ItemMarker itemMarker = AdvancedInventory.getService().getItemMarker();
		this.getInventory().setItem(slot, this.isMarking() ? itemMarker.markItem(item) : item);
		if (clickHandler != null) {
			this.itemClickHandlers.put(slot, clickHandler);
		} else {
			this.itemClickHandlers.remove(slot);
		}
		return this;
	}
	
	public ItemStack @NonNull [] getContents() {
		return this.getInventory().getContents();
	}
	
	public int getSize() {
		return this.getInventory().getSize();
	}
	
	public ItemStack @NonNull [] getItems(final int slotFrom, final int slotTo) {
		if (slotFrom == slotTo || slotFrom > slotTo) {
			throw new IllegalArgumentException("slotFrom must be less than slotTo");
		}
		final ItemStack[] items = new ItemStack[slotTo - slotFrom + 1]; // 20
		for (int i = slotFrom; i <= slotTo; i++) { // 0; i <= 20
			items[i - slotFrom] = this.getInventory().getItem(i);
		}
		return items;
	}
	
	public ItemStack @NonNull [] getItems(final int @NonNull ... slots) {
		final ItemStack[] items = new ItemStack[slots.length];
		for (int i = 0; i < slots.length; i++) {
			items[i] = this.getItem(slots[i]);
		}
		return items;
	}
	
	public ItemStack getItem(final int slot) {
		return this.getInventory().getItem(slot);
	}
	
	@NonNull
	public AdvancedInventory setItem(final int slot, final ItemStack item) {
		return this.setItem(slot, item, null);
	}
	
	@NonNull
	public AdvancedInventory setItems(final int slotFrom, final int slotTo, final ItemStack item) {
		return this.setItems(slotFrom, slotTo, item, null);
	}
	
	@NonNull
	public AdvancedInventory setItems(final int slotFrom, final int slotTo, final ItemStack item, final Consumer<InventoryClickEvent> handler) {
		for (int i = slotFrom; i <= slotTo; i++) {
			this.setItem(i, item, handler);
		}
		return this;
	}
	
	@NonNull
	public AdvancedInventory setItems(final ItemStack item, final int @NonNull ... slots) {
		return this.setItems(item, null, slots);
	}
	
	@NonNull
	public AdvancedInventory setItems(final ItemStack item, final Consumer<InventoryClickEvent> handler, final int @NonNull ... slots) {
		for (final int slot : slots) {
			this.setItem(slot, item, handler);
		}
		return this;
	}
	
	@NonNull
	public AdvancedInventory removeItems(final int @NonNull ... slots) {
		for (final int slot : slots) {
			this.removeItem(slot);
		}
		return this;
	}
	
	@NonNull
	public AdvancedInventory removeItem(final int slot) {
		this.getInventory().clear(slot);
		this.itemClickHandlers.remove(slot);
		return this;
	}
	
	@NonNull
	public AdvancedInventory removeItems(final int slotFrom, final int slotTo) {
		for (int i = slotFrom; i <= slotTo; i++) {
			this.removeItem(i);
		}
		return this;
	}
	
	@NonNull
	public AdvancedInventory clear() {
		this.getInventory().clear();
		return this;
	}
	
	@NonNull
	public AdvancedInventory setCloseFilter(final Predicate<Player> closeFilter) {
		this.closeFilter = closeFilter;
		return this;
	}
	
	@NonNull
	public AdvancedInventory addOpenHandler(final Consumer<InventoryOpenEvent> openHandler) {
		this.openHandlers.add(openHandler);
		return this;
	}
	
	@NonNull
	public AdvancedInventory addCloseHandler(final Consumer<InventoryCloseEvent> closeHandler) {
		this.closeHandlers.add(closeHandler);
		return this;
	}
	
	@NonNull
	public AdvancedInventory addClickHandler(final Consumer<InventoryClickEvent> clickHandler) {
		this.clickHandlers.add(clickHandler);
		return this;
	}
	
	public AdvancedInventory open(final HumanEntity humanEntity) {
		humanEntity.openInventory(this.getInventory());
		return this;
	}
	
	public void onOpen(final InventoryOpenEvent openEvent) {
		// method is called every time when inventory open
	}
	
	public void onClose(final InventoryCloseEvent closeEvent) {
		// method is called every time when inventory closes
	}
	
	public void onClick(final InventoryClickEvent clickEvent) {
		// method is called every time an item is clicked
	}
	
	public void handleOpen(final InventoryOpenEvent openEvent) {
		this.onOpen(openEvent);
		this.openHandlers.forEach(open -> open.accept(openEvent));
	}
	
	public boolean handleClose(final InventoryCloseEvent closeEvent) {
		this.onClose(closeEvent);
		this.closeHandlers.forEach(close -> close.accept( closeEvent));
		return this.closeFilter != null && this.closeFilter.test((Player) closeEvent.getPlayer());
	}
	
	public void handleClick(final InventoryClickEvent clickEvent) {
		this.onClick(clickEvent);
		this.clickHandlers.forEach(click -> click.accept(clickEvent));
		final Consumer<InventoryClickEvent> clickConsumer = this.itemClickHandlers.get(clickEvent.getRawSlot());
		if (clickConsumer != null) {
			clickConsumer.accept(clickEvent);
		}
	}
	
	public AdvancedInventory copy() {
		return new AdvancedInventory(this);
	}
}
