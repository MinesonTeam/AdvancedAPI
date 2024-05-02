package kz.hxncus.mc.minesonapi.inventory;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.listener.EventManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class MSInventoryManager {
    private static MSInventoryManager instance;
    public static void initialize() {
        if (MSInventoryManager.instance != null) {
            throw new RuntimeException("Schedule Manager is already initialized.");
        }
        MSInventoryManager.instance = new MSInventoryManager();
    }

    public static MSInventoryManager getInstance() {
        if (MSInventoryManager.instance == null) {
            throw new RuntimeException("Schedule Manager is not initialized.");
        }
        return MSInventoryManager.instance;
    }

    protected static final Map<Inventory, MSInventory> inventories = new HashMap<>();

    public MSInventoryManager() {
        EventManager eventManager = MinesonAPI.getPlugin().getEventManager();
        eventManager.register(InventoryClickEvent.class, event -> {
            Inventory inventory = event.getInventory();
            MSInventory msInventory = MSInventoryManager.inventories.get(inventory);
            if (event.getClickedInventory() != null && msInventory != null) {
                event.setCancelled(true);

                msInventory.handleClick(event);
            }
        });
        eventManager.register(InventoryOpenEvent.class, event -> {
            Inventory inventory = event.getInventory();
            MSInventory msInventory = MSInventoryManager.inventories.get(inventory);
            if (msInventory != null) {
                msInventory.handleOpen(event);
            }
        });
        eventManager.register(InventoryCloseEvent.class, event -> {
            Inventory inventory = event.getInventory();
            MSInventory msInventory = MSInventoryManager.inventories.get(inventory);
            if (msInventory != null && msInventory.handleClose(event)) {
                msInventory.open((Player) event.getPlayer());
            }
        });
    }

    public void registerInventory(MSInventory inventory) {
        MSInventoryManager.inventories.put(inventory.getInventory(), inventory);
    }

    public void unregisterInventory(MSInventory inventory) {
        MSInventoryManager.inventories.put(inventory.getInventory(), inventory);
    }

    public static void closeAll() {
        MSInventoryManager.inventories.forEach((inventory, msInventory) -> inventory.close());
    }
}
