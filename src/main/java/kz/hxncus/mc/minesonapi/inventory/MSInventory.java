package kz.hxncus.mc.minesonapi.inventory;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.scheduler.Schedule;
import lombok.NonNull;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class MSInventory implements InventoryHolder {
    private Inventory inventory;
    private List<Inventory> inventories;
    private final Map<Integer, Consumer<InventoryClickEvent>> itemHandlers = new HashMap<>();
    private final List<Consumer<InventoryOpenEvent>> openHandlers = new ArrayList<>();
    private final List<Consumer<InventoryCloseEvent>> closeHandlers = new ArrayList<>();
    private final List<Consumer<InventoryClickEvent>> clickHandlers = new ArrayList<>();
    private Predicate<Player> closeFilter;
    @Setter
    private boolean marking = true;

    public MSInventory(Plugin plugin, InventoryType type) {
        this(plugin, holder -> Bukkit.createInventory(holder, type));
    }

    public MSInventory(Plugin plugin, InventoryType type, String title) {
        this(plugin, holder -> Bukkit.createInventory(holder, type, Component.text(title)));
    }

    public MSInventory(Plugin plugin, InventoryType type, Component title) {
        this(plugin, holder -> Bukkit.createInventory(holder, type, title));
    }

    public MSInventory(Plugin plugin, int size) {
        this(plugin, holder -> Bukkit.createInventory(holder, size));
    }

    public MSInventory(Plugin plugin, int size, String title) {
        this(plugin, holder -> Bukkit.createInventory(holder, size, Component.text(title)));
    }

    public MSInventory(Plugin plugin, int size, Component title) {
        this(plugin, holder -> Bukkit.createInventory(holder, size, title));
    }

    public MSInventory(Plugin plugin, Function<InventoryHolder, Inventory> inventoryFunction) {
        Objects.requireNonNull(inventoryFunction, "inventoryFunction is null");
        Inventory inv = inventoryFunction.apply(this);
        if (inv.getHolder() != this) {
            throw new IllegalStateException("Inventory holder is not correct, found: " + inv.getHolder());
        }
        this.inventory = inv;
        new Schedule(plugin, "on inventory initialize").later(1L, this::onInitialize);
    }

    protected void onInitialize() {
    }

    protected void onClick(InventoryClickEvent event) {
    }

    protected void onClose(InventoryCloseEvent event) {
    }

    protected void onOpen(InventoryOpenEvent event) {
    }

    @NonNull
    public MSInventory addItem(ItemStack item) {
        return addItem(item, null);
    }

    @NonNull
    public MSInventory addItem(ItemStack item, Consumer<InventoryClickEvent> handler) {
        int slot = this.inventory.firstEmpty();
        if (slot != -1) {
            setItem(slot, item, handler);
        }
        return this;
    }

    @Nullable
    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    @Nullable
    public ItemStack @NonNull [] getContents() {
        return this.inventory.getContents();
    }

    public int getSize() {
        return this.inventory.getSize();
    }

    @Nullable
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

    @Nullable
    public ItemStack @NonNull [] getItems(int @NonNull... slots) {
        ItemStack[] items = new ItemStack[slots.length];
        for (int i = 0; i < slots.length; i++) {
            items[i] = getItem(slots[i]);
        }
        return items;
    }

    @NonNull
    public MSInventory setItemIf(int slot, ItemStack item, Predicate<MSInventory> predicate) {
        if (predicate.test(this)) {
            return setItem(slot, item);
        }
        return this;
    }

    @NonNull
    public MSInventory setItem(int slot, ItemStack item) {
        return setItem(slot, item, null);
    }

    @NonNull
    public MSInventory setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> handler) {
        this.inventory.setItem(slot, marking ? DupeFixer.getInstance(MinesonAPI.getPlugin()).getInventoryItemMarker().markItem(item) : item);

        if (handler != null) {
            this.itemHandlers.put(slot, handler);
        } else {
            this.itemHandlers.remove(slot);
        }
        return this;
    }

    @NonNull
    public MSInventory setItems(int slotFrom, int slotTo, ItemStack item) {
        return setItems(slotFrom, slotTo, item, null);
    }

    @NonNull
    public MSInventory setItems(int slotFrom, int slotTo, ItemStack item, Consumer<InventoryClickEvent> handler) {
        for (int i = slotFrom; i <= slotTo; i++) {
            setItem(i, item, handler);
        }
        return this;
    }
    @NonNull
    public MSInventory setItems(ItemStack item, int @NonNull ... slots) {
        return setItems(item, null, slots);
    }

    @NonNull
    public MSInventory setItems(ItemStack item, Consumer<InventoryClickEvent> handler, int @NonNull ... slots) {
        for (int slot : slots) {
            setItem(slot, item, handler);
        }
        return this;
    }

    @NonNull
    public MSInventory removeItems(int @NonNull ... slots) {
        for (int slot : slots) {
            removeItem(slot);
        }
        return this;
    }

    @NonNull
    public MSInventory removeItems(int slotFrom, int slotTo) {
        for (int i = slotFrom; i <= slotTo; i++) {
            removeItem(i);
        }
        return this;
    }

    @NonNull
    public MSInventory removeItem(int slot) {
        this.inventory.clear(slot);
        this.itemHandlers.remove(slot);
        return this;
    }

    @NonNull
    public MSInventory setCloseFilter(Predicate<Player> closeFilter) {
        this.closeFilter = closeFilter;
        return this;
    }

    @NonNull
    public MSInventory addOpenHandler(Consumer<InventoryOpenEvent> openHandler) {
        this.openHandlers.add(openHandler);
        return this;
    }

    @NonNull
    public MSInventory addCloseHandler(Consumer<InventoryCloseEvent> closeHandler) {
        this.closeHandlers.add(closeHandler);
        return this;
    }

    @NonNull
    public MSInventory addClickHandler(Consumer<InventoryClickEvent> clickHandler) {
        this.clickHandlers.add(clickHandler);
        return this;
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    @Override
    @NonNull public Inventory getInventory() {
        return this.inventory;
    }

    @Nullable
    public Inventory getInventory(int index) {
        return this.inventories.get(index);
    }

    public void handleOpen(InventoryOpenEvent event) {
        onOpen(event);
        this.openHandlers.forEach(open -> open.accept(event));
    }

    public boolean handleClose(InventoryCloseEvent event) {
        onClose(event);
        this.closeHandlers.forEach(close -> close.accept(event));
        return this.closeFilter != null && this.closeFilter.test((Player) event.getPlayer());
    }

    public void handleClick(InventoryClickEvent event) {
        onClick(event);

        this.clickHandlers.forEach(click -> click.accept(event));

        Consumer<InventoryClickEvent> clickConsumer = this.itemHandlers.get(event.getRawSlot());

        if (clickConsumer != null) {
            clickConsumer.accept(event);
        }
    }
}
