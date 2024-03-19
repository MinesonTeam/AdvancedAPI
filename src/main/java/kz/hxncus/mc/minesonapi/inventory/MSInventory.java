package kz.hxncus.mc.minesonapi.inventory;

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
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class MSInventory implements InventoryHolder {
    private final Inventory inventory;
    private final Map<Integer, Consumer<InventoryClickEvent>> itemHandlers = new HashMap<>();
    private final List<Consumer<InventoryOpenEvent>> openHandlers = new ArrayList<>();
    private final List<Consumer<InventoryCloseEvent>> closeHandlers = new ArrayList<>();
    private final List<Consumer<InventoryClickEvent>> clickHandlers = new ArrayList<>();
    private Predicate<Player> closeFilter;
    protected MSInventory(InventoryType type) {
        this(holder -> Bukkit.createInventory(holder, type));
    }
    protected MSInventory(InventoryType type, String title) {
        this(holder -> Bukkit.createInventory(holder, type, Component.text(title)));
    }
    protected MSInventory(int slots) {
        this(holder -> Bukkit.createInventory(holder, slots));
    }
    protected MSInventory(int slots, String title) {
        this(holder -> Bukkit.createInventory(holder, slots, Component.text(title)));
    }
    protected MSInventory(Function<InventoryHolder, Inventory> inventoryFunction) {
        Objects.requireNonNull(inventoryFunction, "inventoryFunction is null");
        Inventory inv = inventoryFunction.apply(this);

        if (inv.getHolder() != this) {
            throw new IllegalStateException("Inventory holder is not correct, found: " + inv.getHolder());
        }
        this.inventory = inv;
        onInitialize();
    }
    protected abstract void onInitialize();
    protected abstract void onOpen(InventoryOpenEvent event);

    protected abstract void onClick(InventoryClickEvent event);

    protected abstract void onClose(InventoryCloseEvent event);


    public MSInventory addItem(ItemStack item) {
        return addItem(item, null);
    }

    public MSInventory addItem(ItemStack item, Consumer<InventoryClickEvent> handler) {
        int slot = this.inventory.firstEmpty();
        if (slot >= 0) {
            setItem(slot, item, handler);
        }
        return this;
    }

    public MSInventory setItem(int slot, ItemStack item) {
        return setItem(slot, item, null);
    }

    public MSInventory setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> handler) {
        this.inventory.setItem(slot, item);

        if (handler != null) {
            this.itemHandlers.put(slot, handler);
        } else {
            this.itemHandlers.remove(slot);
        }
        return this;
    }

    public MSInventory setItems(int slotFrom, int slotTo, ItemStack item) {
        return setItems(slotFrom, slotTo, item, null);
    }

    public MSInventory setItems(int slotFrom, int slotTo, ItemStack item, Consumer<InventoryClickEvent> handler) {
        for (int i = slotFrom; i <= slotTo; i++) {
            setItem(i, item, handler);
        }
        return this;
    }

    public MSInventory setItems(int[] slots, ItemStack item) {
        return setItems(slots, item, null);
    }

    public MSInventory setItems(int[] slots, ItemStack item, Consumer<InventoryClickEvent> handler) {
        for (int slot : slots) {
            setItem(slot, item, handler);
        }
        return this;
    }

    public MSInventory removeItem(int slot) {
        this.inventory.clear(slot);
        this.itemHandlers.remove(slot);
        return this;
    }

    public MSInventory removeItems(int... slots) {
        for (int slot : slots) {
            removeItem(slot);
        }
        return this;
    }
    public MSInventory removeItems(int slotFrom, int slotTo) {
        for (int i = slotFrom; i <= slotTo; i++) {
            removeItem(i);
        }
        return this;
    }
    public MSInventory setCloseFilter(Predicate<Player> closeFilter) {
        this.closeFilter = closeFilter;
        return this;
    }

    public MSInventory addOpenHandler(Consumer<InventoryOpenEvent> openHandler) {
        this.openHandlers.add(openHandler);
        return this;
    }

    public MSInventory addCloseHandler(Consumer<InventoryCloseEvent> closeHandler) {
        this.closeHandlers.add(closeHandler);
        return this;
    }

    public MSInventory addClickHandler(Consumer<InventoryClickEvent> clickHandler) {
        this.clickHandlers.add(clickHandler);
        return this;
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public void handleOpen(InventoryOpenEvent event) {
        onOpen(event);
        this.openHandlers.forEach(open -> open.accept(event));
    }

    public void handleClose(InventoryCloseEvent event) {
        onClose(event);
        this.closeHandlers.forEach(close -> close.accept(event));
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
