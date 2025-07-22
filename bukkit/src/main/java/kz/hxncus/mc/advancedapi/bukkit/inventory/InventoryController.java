package kz.hxncus.mc.advancedapi.bukkit.inventory;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.annotation.Inject;
import kz.hxncus.mc.advancedapi.annotation.Provide;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.Clickable;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.OpenCloseable;
import kz.hxncus.mc.advancedapi.api.bukkit.inventory.marker.ItemMarker;
import kz.hxncus.mc.advancedapi.bukkit.event.EventDispatcher;
import kz.hxncus.mc.advancedapi.bukkit.inventory.marker.PDCItemMarker;
import kz.hxncus.mc.advancedapi.bukkit.inventory.marker.UnavailableItemMarker;
import kz.hxncus.mc.advancedapi.utility.VersionUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
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
@Provide
@ToString
@EqualsAndHashCode(callSuper = false)
public class InventoryController {
	protected static final Map<Inventory, AdvancedInventory> inventories = new ConcurrentHashMap<>();
	@Inject
	private static AdvancedAPI plugin;
	private final ItemMarker itemMarker;
	
	public InventoryController() {
		this.itemMarker = this.getItemMarker(plugin);
	}
	
	private ItemMarker getItemMarker(final AdvancedAPI plugin) {
		if (VersionUtil.isAfterOrEqual(1140)) {
			return new PDCItemMarker(plugin);
		} else {
			return new UnavailableItemMarker();
		}
	}
	
	public AdvancedInventory getInventory(final Inventory inventory) {
		return inventories.get(inventory);
	}
	
	public void registerEvents(final EventDispatcher eventManager) {
		eventManager.register(EntityPickupItemEvent.class, event -> {
			if (this.itemMarker.isItemMarked(event.getItem().getItemStack())) {
				event.setCancelled(true);
				event.getItem().remove();
				InventoryController.plugin.getLogger().info("Someone picked up a Custom Inventory item. Removing it.");
			}
		});
		eventManager.register(EntityDropItemEvent.class, event -> {
			if (this.itemMarker.isItemMarked(event.getItemDrop().getItemStack())) {
				event.setCancelled(true);
				event.getItemDrop().remove();
				InventoryController.plugin.getLogger().info("Someone dropped a Custom Inventory item. Removing it.");
			}
		});
		eventManager.register(PlayerLoginEvent.class, event -> InventoryController.plugin.getServer().getScheduler()
			.runTaskLater(InventoryController.plugin, () -> {
				final PlayerInventory inventory = event.getPlayer().getInventory();
				for (final ItemStack item : inventory) {
					if (item != null && this.itemMarker.isItemMarked(item)) {
						inventory.remove(item);
						InventoryController.plugin.getLogger().info("Player logged in with a Custom Inventory item in their inventory. Removing it.");
					}
				}
			}, 10L)
		);
		eventManager.register(InventoryClickEvent.class, event -> {
			final Clickable advancedInventory = inventories.get(event.getInventory());
			if (event.getClickedInventory() != null && advancedInventory != null) {
				advancedInventory.handleClick(event);
			}
		});
		eventManager.register(InventoryOpenEvent.class, event -> {
			final OpenCloseable advancedInventory = inventories.get(event.getInventory());
			if (advancedInventory != null) {
				advancedInventory.handleOpen(event);
			}
		});
		eventManager.register(InventoryCloseEvent.class, event -> {
			final OpenCloseable advancedInventory = inventories.get(event.getInventory());
			if (advancedInventory != null && advancedInventory.handleClose(event)) {
				advancedInventory.open(event.getPlayer());
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
			}
		);
	}
	
	public void registerInventory(final AdvancedInventory inventory) {
		inventories.put(inventory.getInventory(), inventory);
	}
	
	public void unregisterInventory(final AdvancedInventory inventory) {
		inventories.put(inventory.getInventory(), inventory);
	}
}
