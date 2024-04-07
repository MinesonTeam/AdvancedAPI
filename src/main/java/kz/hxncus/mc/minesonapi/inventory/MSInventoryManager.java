package kz.hxncus.mc.minesonapi.inventory;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.listener.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class MSInventoryManager {
    private static MSInventoryManager instance;
    public static MSInventoryManager getInstance() {
        if (MSInventoryManager.instance == null) {
            MSInventoryManager.instance = new MSInventoryManager();
        }
        return MSInventoryManager.instance;
    }

    private MSInventoryManager() {
        EventManager.getInstance().register(MinesonAPI.getPlugin(), InventoryClickEvent.class, event -> {
            if (event.getInventory().getHolder() instanceof MSInventory inventory && event.getClickedInventory() != null) {
                boolean wasCancelled = event.isCancelled();
                event.setCancelled(true);

                inventory.handleClick(event);

                // This prevents un-canceling the event if another plugin canceled it before
                if (!wasCancelled && !event.isCancelled()) {
                    event.setCancelled(false);
                }
            }
        }).register(MinesonAPI.getPlugin(), InventoryOpenEvent.class, event -> {
            if (event.getInventory().getHolder() instanceof MSInventory inventory) {
                inventory.handleOpen(event);
            }
        }).register(MinesonAPI.getPlugin(), InventoryCloseEvent.class, event -> {
            if (event.getInventory().getHolder() instanceof MSInventory inventory && (inventory.handleClose(event))) {
                    Bukkit.getScheduler().runTask(MinesonAPI.getPlugin(), () -> inventory.open((Player) event.getPlayer()));
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
