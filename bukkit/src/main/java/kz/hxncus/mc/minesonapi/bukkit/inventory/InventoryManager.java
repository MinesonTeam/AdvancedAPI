package kz.hxncus.mc.minesonapi.bukkit.inventory;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.bukkit.event.EventManager;
import kz.hxncus.mc.minesonapi.bukkit.inventory.marker.ItemMarker;
import kz.hxncus.mc.minesonapi.bukkit.inventory.marker.PDCItemMarker;
import kz.hxncus.mc.minesonapi.bukkit.inventory.marker.UnavailableItemMarker;
import kz.hxncus.mc.minesonapi.util.VersionUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Bukkit;
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
@EqualsAndHashCode
public class InventoryManager {
	protected static final Map<Inventory, SimpleInventory> inventories = new ConcurrentHashMap<>();
	private final MinesonAPI plugin;
	private final ItemMarker itemMarker;
	
	public InventoryManager(final MinesonAPI plugin) {
		this.plugin = plugin;
		this.itemMarker = this.getItemMarker(plugin);
		this.registerEvents(plugin.getEventManager());
	}
	
	private ItemMarker getItemMarker(final MinesonAPI plugin) {
		if (VersionUtil.isAfterOrEqual(1140)) {
			return new PDCItemMarker(plugin);
		} else {
			return new UnavailableItemMarker();
		}
	}
	
	public void registerEvents(final EventManager eventManager) {
		eventManager.register(EntityPickupItemEvent.class, event -> {
			if (this.itemMarker.isItemMarked(event.getItem()
			                                      .getItemStack())) {
				event.setCancelled(true);
				event.getItem()
				     .remove();
				this.plugin.getLogger()
				           .info("Someone picked up a Custom Inventory item. Removing it.");
			}
		});
		eventManager.register(EntityDropItemEvent.class, event -> {
			if (this.itemMarker.isItemMarked(event.getItemDrop()
			                                      .getItemStack())) {
				event.setCancelled(true);
				event.getItemDrop()
				     .remove();
				this.plugin.getLogger()
				           .info("Someone dropped a Custom Inventory item. Removing it.");
			}
		});
		eventManager.register(PlayerLoginEvent.class, event -> this.plugin.getServer()
		                                                                  .getScheduler()
		                                                                  .runTaskLater(this.plugin, () -> {
			                                                                  final PlayerInventory inventory = event.getPlayer()
			                                                                                                         .getInventory();
			                                                                  for (final ItemStack item : inventory) {
				                                                                  if (item != null && this.itemMarker.isItemMarked(item)) {
					                                                                  inventory.remove(item);
					                                                                  this.plugin.getLogger()
					                                                                             .info("Player logged in with a Custom Inventory item in their inventory. Removing it.");
				                                                                  }
			                                                                  }
		                                                                  }, 10L)
		);
		eventManager.register(InventoryClickEvent.class, event -> {
			final SimpleInventory simpleInventory = inventories.get(event.getInventory());
			if (event.getClickedInventory() != null && simpleInventory != null) {
				simpleInventory.handleClick(event);
			}
		});
		eventManager.register(InventoryOpenEvent.class, event -> {
			final SimpleInventory simpleInventory = inventories.get(event.getInventory());
			if (simpleInventory != null) {
				simpleInventory.handleOpen(event);
			}
		});
		eventManager.register(InventoryCloseEvent.class, event -> {
			final SimpleInventory simpleInventory = inventories.get(event.getInventory());
			if (simpleInventory != null && simpleInventory.handleClose(event)) {
				simpleInventory.open(event.getPlayer());
			}
		});
	}
	
	public void closeAll() {
		Bukkit.getOnlinePlayers()
		      .forEach(player -> {
			      if (inventories.containsKey(player.getOpenInventory()
			                                        .getTopInventory())) {
				      player.closeInventory();
			      }
		      });
	}
	
	public void registerInventory(final SimpleInventory inventory) {
		inventories.put(inventory.getInventory(), inventory);
	}
	
	public void unregisterInventory(final SimpleInventory inventory) {
		inventories.put(inventory.getInventory(), inventory);
	}
}
