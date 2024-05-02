package kz.hxncus.mc.minesonapi.inventory;

import kz.hxncus.mc.minesonapi.MinesonAPI;
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
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MSInventory {
    private Inventory inventory;
    private List<Inventory> pagination;
    private final Map<Integer, Consumer<InventoryClickEvent>> itemHandlers = new HashMap<>();
    private final List<Consumer<InventoryOpenEvent>> openHandlers = new ArrayList<>();
    private final List<Consumer<InventoryCloseEvent>> closeHandlers = new ArrayList<>();
    private final List<Consumer<InventoryClickEvent>> clickHandlers = new ArrayList<>();
    private Predicate<Player> closeFilter;
    @Setter
    private boolean marking = true;

    public MSInventory(InventoryType type) {
        this(Bukkit.createInventory(null, type));
    }

    public MSInventory(InventoryType type, String title) {
        this(Bukkit.createInventory(null, type, Component.text(title)));
    }

    public MSInventory(InventoryType type, Component title) {
        this(Bukkit.createInventory(null, type, title));
    }

    public MSInventory(int size) {
        this(Bukkit.createInventory(null, size));
    }

    public MSInventory(int size, String title) {
        this(Bukkit.createInventory(null, size, Component.text(title)));
    }

    public MSInventory(int size, Component title) {
        this(Bukkit.createInventory(null, size, title));
    }

    public MSInventory(@NonNull Inventory inventory) {
        this.inventory = inventory;
        registerInventory();
        Bukkit.getScheduler().runTaskLater(
              MinesonAPI.getPlugin(), this::onInitialize, 1L
        );
    }

    public void registerInventory() {
        MSInventoryManager.getInstance().registerInventory(this);
    }

    public void unregisterInventory() {
        MSInventoryManager.getInstance().unregisterInventory(this);
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
    public MSInventory clear() {
        this.inventory.clear();
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

    @NonNull
    public Inventory getInventory() {
        return this.inventory;
    }

    @Nullable
    public Inventory getInventory(int index) {
        return this.pagination.get(index);
    }

    public int close() {
        return this.getInventory().close();
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
