package kz.hxncus.mc.minesonapi.inventory;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.inventory.marker.InventoryItemMarker;
import kz.hxncus.mc.minesonapi.listener.EventManager;
import lombok.Getter;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.logging.Logger;

public class DupeFixer {
    @Getter
    private InventoryItemMarker inventoryItemMarker;
    public DupeFixer(MinesonAPI plugin) {
        this.inventoryItemMarker = new InventoryItemMarker(plugin, "MS");
        EventManager eventManager = EventManager.getInstance(plugin);
        Logger logger = plugin.getLogger();
        eventManager.register(EntityPickupItemEvent.class, event -> {
            Item item = event.getItem();
            if (inventoryItemMarker.isItemMarked(item.getItemStack())) {
                logger.info("Someone picked up a Custom Inventory item. Removing it.");
                event.setCancelled(true);
                item.remove();
            }
        });

        eventManager.register(EntityDropItemEvent.class, event -> {
            Item item = event.getItemDrop();
            if (inventoryItemMarker.isItemMarked(item.getItemStack())) {
                logger.info("Someone dropped a Custom Inventory item. Removing it.");
                event.setCancelled(true);
                item.remove();
            }
        });

        eventManager.register(PlayerLoginEvent.class, event -> {
            plugin.getServer()
                  .getScheduler()
                  .runTaskLater(plugin, () -> {
                      PlayerInventory inventory = event.getPlayer().getInventory();
                      for (ItemStack itemStack : inventory) {
                          if (itemStack != null && inventoryItemMarker.isItemMarked(itemStack)) {
                              logger.info("Player logged in with a Custom Inventory item in their inventory. Removing it.");
                              inventory.remove(itemStack);
                          }
                      }
                  }, 10L);
        });
    }
}
