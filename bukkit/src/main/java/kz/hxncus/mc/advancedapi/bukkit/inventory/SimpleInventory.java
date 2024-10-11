package kz.hxncus.mc.advancedapi.bukkit.inventory;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.bukkit.scheduler.Scheduler;
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
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class SimpleInventory {
	@Getter
	private final Inventory inventory;
	private final List<SimpleInventory> inventories = new ArrayList<>();
	private final Map<Integer, BiConsumer<SimpleInventory, InventoryClickEvent>> itemHandlers = new HashMap<>();
	private final List<BiConsumer<SimpleInventory, InventoryOpenEvent>> openHandlers = new ArrayList<>();
	private final List<BiConsumer<SimpleInventory, InventoryCloseEvent>> closeHandlers = new ArrayList<>();
	private final List<BiConsumer<SimpleInventory, InventoryClickEvent>> clickHandlers = new ArrayList<>();
	private Predicate<Player> closeFilter;
	@Setter
	private boolean marking = true;
	
	public SimpleInventory(final InventoryType type) {
		this(Bukkit.createInventory(null, type));
	}
	
	public SimpleInventory(@NonNull final Inventory inventory) {
		this.inventory = inventory;
		this.registerInventory();
	}
	
	private void registerInventory() {
		final AdvancedAPI plugin = AdvancedAPI.getInstance();
		
		plugin.getInventoryService()
		      .registerInventory(this);
		Scheduler.later(1L, this::onInitialize);
	}
	
	protected void onInitialize() {
		// method is called when the inventory is initialized
	}
	
	public SimpleInventory(final InventoryType type, final String title) {
		this(Bukkit.createInventory(null, type, title));
	}
	
	public SimpleInventory(final int size) {
		this(Bukkit.createInventory(null, size));
	}
	
	public SimpleInventory(final int size, final String title) {
		this(Bukkit.createInventory(null, size, title));
	}
	
	public SimpleInventory(@NonNull final SimpleInventory simpleInventory) {
		this.inventory = simpleInventory.inventory;
		this.inventories.addAll(simpleInventory.inventories);
		this.itemHandlers.putAll(simpleInventory.itemHandlers);
		this.openHandlers.addAll(simpleInventory.openHandlers);
		this.closeHandlers.addAll(simpleInventory.closeHandlers);
		this.clickHandlers.addAll(simpleInventory.clickHandlers);
		this.closeFilter = simpleInventory.closeFilter;
		this.marking = simpleInventory.marking;
		this.registerInventory();
	}
	
	public SimpleInventory getPage(final int pageIndex) {
		return this.inventories.get(pageIndex);
	}
	
	public SimpleInventory addPages(final SimpleInventory... inventories) {
		for (final SimpleInventory simpleInventory : inventories) {
			this.addPage(simpleInventory);
		}
		return this;
	}
	
	public SimpleInventory addPage(final SimpleInventory inventory) {
		this.inventories.add(inventory);
		inventory.inventories.add(this);
		return this;
	}
	
	public SimpleInventory setPage(final int pageIndex, final SimpleInventory inventory) {
		this.inventories.set(pageIndex, inventory);
		return this;
	}
	
	@NonNull
	public SimpleInventory addItem(final ItemStack item) {
		return this.addItem(item, null);
	}
	
	@NonNull
	public SimpleInventory addItem(final ItemStack item, final BiConsumer<SimpleInventory, InventoryClickEvent> handler) {
		final int slot = this.inventory.firstEmpty();
		if (slot != -1) {
			this.setItem(slot, item, handler);
		}
		return this;
	}
	
	@NonNull
	public SimpleInventory setItem(final int slot, final ItemStack item, final BiConsumer<SimpleInventory, InventoryClickEvent> handler) {
		this.inventory.setItem(slot, this.marking ? AdvancedAPI.getInstance()
		                                                       .getInventoryService()
		                                                       .getItemMarker()
		                                                       .markItem(item) : item);
		if (handler != null) {
			this.itemHandlers.put(slot, handler);
		} else {
			this.itemHandlers.remove(slot);
		}
		return this;
	}
	
	public ItemStack @NonNull [] getContents() {
		return this.inventory.getContents();
	}
	
	public int getSize() {
		return this.inventory.getSize();
	}
	
	public ItemStack @NonNull [] getItems(final int slotFrom, final int slotTo) {
		if (slotFrom == slotTo || slotFrom > slotTo) {
			throw new IllegalArgumentException("slotFrom must be less than slotTo");
		}
		final ItemStack[] items = new ItemStack[slotTo - slotFrom + 1]; // 20
		for (int i = slotFrom; i <= slotTo; i++) { // 0; i <= 20
			items[i - slotFrom] = this.inventory.getItem(i);
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
		return this.inventory.getItem(slot);
	}
	
	@NonNull
	public SimpleInventory setItemIf(final int slot, final ItemStack item, final Predicate<SimpleInventory> predicate) {
		if (predicate.test(this)) {
			return this.setItem(slot, item);
		}
		return this;
	}
	
	@NonNull
	public SimpleInventory setItem(final int slot, final ItemStack item) {
		return this.setItem(slot, item, null);
	}
	
	@NonNull
	public SimpleInventory setItems(final int slotFrom, final int slotTo, final ItemStack item) {
		return this.setItems(slotFrom, slotTo, item, null);
	}
	
	@NonNull
	public SimpleInventory setItems(final int slotFrom, final int slotTo, final ItemStack item, final BiConsumer<SimpleInventory, InventoryClickEvent> handler) {
		for (int i = slotFrom; i <= slotTo; i++) {
			this.setItem(i, item, handler);
		}
		return this;
	}
	
	@NonNull
	public SimpleInventory setItems(final ItemStack item, final int @NonNull ... slots) {
		return this.setItems(item, null, slots);
	}
	
	@NonNull
	public SimpleInventory setItems(final ItemStack item, final BiConsumer<SimpleInventory, InventoryClickEvent> handler, final int @NonNull ... slots) {
		for (final int slot : slots) {
			this.setItem(slot, item, handler);
		}
		return this;
	}
	
	@NonNull
	public SimpleInventory removeItems(final int @NonNull ... slots) {
		for (final int slot : slots) {
			this.removeItem(slot);
		}
		return this;
	}
	
	@NonNull
	public SimpleInventory removeItem(final int slot) {
		this.inventory.clear(slot);
		this.itemHandlers.remove(slot);
		return this;
	}
	
	@NonNull
	public SimpleInventory removeItems(final int slotFrom, final int slotTo) {
		for (int i = slotFrom; i <= slotTo; i++) {
			this.removeItem(i);
		}
		return this;
	}
	
	@NonNull
	public SimpleInventory clear() {
		this.inventory.clear();
		return this;
	}
	
	@NonNull
	public SimpleInventory setCloseFilter(final Predicate<Player> closeFilter) {
		this.closeFilter = closeFilter;
		return this;
	}
	
	@NonNull
	public SimpleInventory addOpenHandler(final BiConsumer<SimpleInventory, InventoryOpenEvent> openHandler) {
		this.openHandlers.add(openHandler);
		return this;
	}
	
	@NonNull
	public SimpleInventory addCloseHandler(final BiConsumer<SimpleInventory, InventoryCloseEvent> closeHandler) {
		this.closeHandlers.add(closeHandler);
		return this;
	}
	
	@NonNull
	public SimpleInventory addClickHandler(final BiConsumer<SimpleInventory, InventoryClickEvent> clickHandler) {
		this.clickHandlers.add(clickHandler);
		return this;
	}
	
	public SimpleInventory open(final HumanEntity humanEntity) {
		humanEntity.openInventory(this.inventory);
		return this;
	}
	
	public void handleOpen(final InventoryOpenEvent event) {
		this.onOpen(event);
		this.openHandlers.forEach(open -> open.accept(this, event));
	}
	
	protected void onOpen(final InventoryOpenEvent event) {
		// method is called every time when inventory open
	}
	
	public boolean handleClose(final InventoryCloseEvent event) {
		this.onClose(event);
		this.closeHandlers.forEach(close -> close.accept(this, event));
		return this.closeFilter != null && this.closeFilter.test((Player) event.getPlayer());
	}
	
	protected void onClose(final InventoryCloseEvent event) {
		// method is called every time when inventory closes
	}
	
	public void handleClick(final InventoryClickEvent event) {
		this.onClick(event);
		
		this.clickHandlers.forEach(click -> click.accept(this, event));
		
		final BiConsumer<SimpleInventory, InventoryClickEvent> clickConsumer = this.itemHandlers.get(event.getRawSlot());
		
		if (clickConsumer != null) {
			clickConsumer.accept(this, event);
		}
	}
	
	protected void onClick(final InventoryClickEvent event) {
		// method is called every time an item is clicked
	}
	
	public SimpleInventory copy() {
		return new SimpleInventory(this);
	}
}
