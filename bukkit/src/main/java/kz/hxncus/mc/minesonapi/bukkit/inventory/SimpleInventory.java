package kz.hxncus.mc.minesonapi.bukkit.inventory;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.bukkit.scheduler.Scheduler;
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

    public SimpleInventory(InventoryType type) {
        this(Bukkit.createInventory(null, type));
    }

    public SimpleInventory(InventoryType type, String title) {
        this(Bukkit.createInventory(null, type, title));
    }

    public SimpleInventory(int size) {
        this(Bukkit.createInventory(null, size));
    }

    public SimpleInventory(int size, String title) {
        this(Bukkit.createInventory(null, size, title));
    }

    public SimpleInventory(@NonNull SimpleInventory simpleInventory) {
        this.inventory = simpleInventory.getInventory();
        this.inventories.addAll(simpleInventory.inventories);
        this.itemHandlers.putAll(simpleInventory.itemHandlers);
        this.openHandlers.addAll(simpleInventory.openHandlers);
        this.closeHandlers.addAll(simpleInventory.closeHandlers);
        this.clickHandlers.addAll(simpleInventory.clickHandlers);
        this.closeFilter = simpleInventory.closeFilter;
        this.marking = simpleInventory.marking;
        registerInventory();
    }

    public SimpleInventory(@NonNull Inventory inventory) {
        this.inventory = inventory;
        registerInventory();
    }

    private void registerInventory() {
        MinesonAPI plugin = MinesonAPI.get();

        plugin.getInventoryManager().registerInventory(this);
        Scheduler.later(1L, this::onInitialize);
    }

    protected void onInitialize() {
        // method is called when the inventory is initialized
    }

    protected void onClick(InventoryClickEvent event) {
        // method is called every time an item is clicked
    }

    protected void onClose(InventoryCloseEvent event) {
        // method is called every time when inventory closes
    }

    protected void onOpen(InventoryOpenEvent event) {
        // method is called every time when inventory open
    }

    public SimpleInventory getPage(int pageIndex) {
        return inventories.get(pageIndex);
    }

    public SimpleInventory addPage(SimpleInventory inventory) {
        this.inventories.add(inventory);
        inventory.inventories.add(this);
        return this;
    }

    public SimpleInventory addPages(SimpleInventory... inventories) {
        for (SimpleInventory simpleInventory : inventories) {
            addPage(simpleInventory);
        }
        return this;
    }

    public SimpleInventory setPage(int pageIndex, SimpleInventory inventory) {
        this.inventories.set(pageIndex, inventory);
        return this;
    }

    @NonNull
    public SimpleInventory addItem(ItemStack item) {
        return addItem(item, null);
    }

    @NonNull
    public SimpleInventory addItem(ItemStack item, BiConsumer<SimpleInventory, InventoryClickEvent> handler) {
        int slot = this.inventory.firstEmpty();
        if (slot != -1) {
            setItem(slot, item, handler);
        }
        return this;
    }

    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    public ItemStack @NonNull [] getContents() {
        return this.inventory.getContents();
    }

    public int getSize() {
        return this.inventory.getSize();
    }

    public ItemStack @NonNull [] getItems(int slotFrom, int slotTo) {
        if (slotFrom == slotTo || slotFrom > slotTo) {
            throw new IllegalArgumentException("slotFrom must be less than slotTo");
        }
        ItemStack[] items = new ItemStack[slotTo - slotFrom + 1]; // 20
        for (int i = slotFrom ; i <= slotTo; i++) { // 0; i <= 20
            items[i - slotFrom] = this.inventory.getItem(i);
        }
        return items;
    }

    public ItemStack @NonNull [] getItems(int @NonNull... slots) {
        ItemStack[] items = new ItemStack[slots.length];
        for (int i = 0; i < slots.length; i++) {
            items[i] = getItem(slots[i]);
        }
        return items;
    }

    @NonNull
    public SimpleInventory setItemIf(int slot, ItemStack item, Predicate<SimpleInventory> predicate) {
        if (predicate.test(this)) {
            return setItem(slot, item);
        }
        return this;
    }

    @NonNull
    public SimpleInventory setItem(int slot, ItemStack item) {
        return setItem(slot, item, null);
    }

    @NonNull
    public SimpleInventory setItem(int slot, ItemStack item, BiConsumer<SimpleInventory, InventoryClickEvent> handler) {
        this.inventory.setItem(slot, marking ? MinesonAPI.get().getInventoryManager().getItemMarker().markItem(item) : item);
        if (handler != null) {
            this.itemHandlers.put(slot, handler);
        } else {
            this.itemHandlers.remove(slot);
        }
        return this;
    }

    @NonNull
    public SimpleInventory setItems(int slotFrom, int slotTo, ItemStack item) {
        return setItems(slotFrom, slotTo, item, null);
    }

    @NonNull
    public SimpleInventory setItems(int slotFrom, int slotTo, ItemStack item, BiConsumer<SimpleInventory, InventoryClickEvent> handler) {
        for (int i = slotFrom; i <= slotTo; i++) {
            setItem(i, item, handler);
        }
        return this;
    }

    @NonNull
    public SimpleInventory setItems(ItemStack item, int @NonNull ... slots) {
        return setItems(item, null, slots);
    }

    @NonNull
    public SimpleInventory setItems(ItemStack item, BiConsumer<SimpleInventory, InventoryClickEvent> handler, int @NonNull ... slots) {
        for (int slot : slots) {
            setItem(slot, item, handler);
        }
        return this;
    }

    @NonNull
    public SimpleInventory removeItems(int @NonNull ... slots) {
        for (int slot : slots) {
            removeItem(slot);
        }
        return this;
    }

    @NonNull
    public SimpleInventory removeItems(int slotFrom, int slotTo) {
        for (int i = slotFrom; i <= slotTo; i++) {
            removeItem(i);
        }
        return this;
    }

    @NonNull
    public SimpleInventory removeItem(int slot) {
        this.inventory.clear(slot);
        this.itemHandlers.remove(slot);
        return this;
    }

    @NonNull
    public SimpleInventory clear() {
        this.inventory.clear();
        return this;
    }

    @NonNull
    public SimpleInventory setCloseFilter(Predicate<Player> closeFilter) {
        this.closeFilter = closeFilter;
        return this;
    }

    @NonNull
    public SimpleInventory addOpenHandler(BiConsumer<SimpleInventory, InventoryOpenEvent> openHandler) {
        this.openHandlers.add(openHandler);
        return this;
    }

    @NonNull
    public SimpleInventory addCloseHandler(BiConsumer<SimpleInventory, InventoryCloseEvent> closeHandler) {
        this.closeHandlers.add(closeHandler);
        return this;
    }

    @NonNull
    public SimpleInventory addClickHandler(BiConsumer<SimpleInventory, InventoryClickEvent> clickHandler) {
        this.clickHandlers.add(clickHandler);
        return this;
    }

    public SimpleInventory open(HumanEntity humanEntity) {
        humanEntity.openInventory(this.inventory);
        return this;
    }


    public void handleOpen(InventoryOpenEvent event) {
        onOpen(event);
        this.openHandlers.forEach(open -> open.accept(this, event));
    }

    public boolean handleClose(InventoryCloseEvent event) {
        onClose(event);
        this.closeHandlers.forEach(close -> close.accept(this, event));
        return this.closeFilter != null && this.closeFilter.test((Player) event.getPlayer());
    }

    public void handleClick(InventoryClickEvent event) {
        onClick(event);

        this.clickHandlers.forEach(click -> click.accept(this, event));

        BiConsumer<SimpleInventory, InventoryClickEvent> clickConsumer = this.itemHandlers.get(event.getRawSlot());

        if (clickConsumer != null) {
            clickConsumer.accept(this, event);
        }
    }

    public SimpleInventory copy() {
        return new SimpleInventory(this);
    }
}
