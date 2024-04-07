package kz.hxncus.mc.minesonapi.inventory;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.listener.EventManager;
import kz.hxncus.mc.minesonapi.listener.PluginDisablingEvent;
import kz.hxncus.mc.minesonapi.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.Plugin;

public class MSInventoryManager {
    public MSInventoryManager(Plugin plugin) {
        EventManager eventManager = EventManager.getInstance(plugin);
        eventManager.register(InventoryClickEvent.class, event -> {
            if (event.getInventory().getHolder() instanceof MSInventory inventory && event.getClickedInventory() != null) {
                boolean wasCancelled = event.isCancelled();
                event.setCancelled(true);

                inventory.handleClick(event);

                // This prevents un-canceling the event if another plugin canceled it before
                if (!wasCancelled && !event.isCancelled()) {
                    event.setCancelled(false);
                }
            }
        });
        eventManager.register(InventoryOpenEvent.class, event -> {
            if (event.getInventory().getHolder() instanceof MSInventory inventory) {
                inventory.handleOpen(event);
            }
        });
        eventManager.register(InventoryCloseEvent.class, event -> {
            if (event.getInventory().getHolder() instanceof MSInventory inventory && (inventory.handleClose(event))) {
                    Bukkit.getScheduler().runTask(plugin, () -> inventory.open((Player) event.getPlayer()));

            }
        });
        eventManager.register(PluginDisablingEvent.class, EventPriority.LOWEST, event -> {
            if (event.getPlugin() == plugin) {
                closeAll();
            }
        });
    }

    public void closeAll() {
        Bukkit.getOnlinePlayers().stream()
              .filter(p -> p.getOpenInventory()
                            .getTopInventory()
                            .getHolder() instanceof MSInventory)
              .forEach(Player::closeInventory);
    }
}
