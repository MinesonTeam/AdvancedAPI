package kz.hxncus.mc.minesonapi.bukkit.inventory;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.bukkit.event.EventManager;
import kz.hxncus.mc.minesonapi.bukkit.inventory.marker.ItemMarker;
import kz.hxncus.mc.minesonapi.bukkit.inventory.marker.PDCItemMarker;
import kz.hxncus.mc.minesonapi.bukkit.inventory.marker.UnavailableItemMarker;
import kz.hxncus.mc.minesonapi.util.Versions;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class InventoryManager {
    protected static final Map<Inventory, SimpleInventory> inventories = new ConcurrentHashMap<>();
    private final MinesonAPI plugin;
    private final ItemMarker itemMarker;

    public InventoryManager(MinesonAPI plugin) {
        this.plugin = plugin;
        this.itemMarker = getItemMarker(plugin);
        registerEvents(plugin.getEventManager());
    }

    private ItemMarker getItemMarker(MinesonAPI plugin) {
        if (Versions.afterOrEqual(14)) {
            return new PDCItemMarker(plugin);
        } else {
            return new UnavailableItemMarker();
        }
    }

    public void registerInventory(SimpleInventory inventory) {
        inventories.put(inventory.getInventory(), inventory);
    }

    public void unregisterInventory(SimpleInventory inventory) {
        inventories.put(inventory.getInventory(), inventory);
    }

    public static void closeAll() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (inventories.containsKey(player.getOpenInventory().getTopInventory())) {
                player.closeInventory();
            }
        });
    }

    public void registerEvents(EventManager eventManager) {
        eventManager.register(EntityPickupItemEvent.class, event -> {
            if (itemMarker.isItemMarked(event.getItem().getItemStack())) {
                event.setCancelled(true);
                event.getItem().remove();
                plugin.getLogger().info("Someone picked up a Custom Inventory item. Removing it.");
            }
        });
        eventManager.register(EntityDropItemEvent.class, event -> {
            if (itemMarker.isItemMarked(event.getItemDrop().getItemStack())) {
                event.setCancelled(true);
                event.getItemDrop().remove();
                plugin.getLogger().info("Someone dropped a Custom Inventory item. Removing it.");
            }
        });
        eventManager.register(PlayerLoginEvent.class, event -> plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            PlayerInventory inventory = event.getPlayer().getInventory();
            for (ItemStack item : inventory) {
                if (item != null && itemMarker.isItemMarked(item)) {
                    inventory.remove(item);
                    plugin.getLogger().info("Player logged in with a Custom Inventory item in their inventory. Removing it.");
                }
            }}, 10L)
        );
        eventManager.register(InventoryClickEvent.class, event -> {
            SimpleInventory simpleInventory = inventories.get(event.getInventory());
            if (event.getClickedInventory() != null && simpleInventory != null) {
                simpleInventory.handleClick(event);
            }
        });
        eventManager.register(InventoryOpenEvent.class, event -> {
            SimpleInventory simpleInventory = inventories.get(event.getInventory());
            if (simpleInventory != null) {
                simpleInventory.handleOpen(event);
            }
        });
        eventManager.register(InventoryCloseEvent.class, event -> {
            SimpleInventory simpleInventory = inventories.get(event.getInventory());
            if (simpleInventory != null && simpleInventory.handleClose(event)) {
                simpleInventory.open((Player) event.getPlayer());
            }
        });
    }
}
