package kz.hxncus.mc.advancedapi.bukkit.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

public class CustomEvents {
    private final EventService eventService;

    public CustomEvents(@NonNull EventService eventService) {
        this.eventService = eventService;
    }

    public void registerEvents() {
        this.registerPlayerInteractEvent();
        this.registerPlayerStatisticIncrementEvent();
        this.registerInventoryClickEvent();
        this.registerBlockDispenseArmorEvent();
    }

    private void registerPlayerInteractEvent() {
        this.eventService.register(PlayerInteractEvent.class, interactEvent -> {
			final Action action = interactEvent.getAction();
			switch (action) {
				case LEFT_CLICK_BLOCK:
				case LEFT_CLICK_AIR:
					PlayerLeftClickEvent leftClickEvent = new PlayerLeftClickEvent(interactEvent.getPlayer(), interactEvent.getAction(),
                        interactEvent.getItem(), interactEvent.getClickedBlock(), interactEvent.getBlockFace(), interactEvent.getHand(),
                        interactEvent.getClickedPosition());
					this.eventService.callEvent(leftClickEvent);
					interactEvent.setCancelled(leftClickEvent.isCancelled());
					break;
				case RIGHT_CLICK_BLOCK:
				case RIGHT_CLICK_AIR:
					PlayerRightClickEvent rightClickEvent = new PlayerRightClickEvent(interactEvent.getPlayer(), interactEvent.getAction(),
                        interactEvent.getItem(), interactEvent.getClickedBlock(), interactEvent.getBlockFace(), interactEvent.getHand(),
                        interactEvent.getClickedPosition());
					this.eventService.callEvent(rightClickEvent);
					interactEvent.setCancelled(rightClickEvent.isCancelled());
					break;
				case PHYSICAL:
					PlayerPhysicalInteractEvent physicalInteractEvent = new PlayerPhysicalInteractEvent(interactEvent.getPlayer(), interactEvent.getAction(),
                        interactEvent.getItem(), interactEvent.getClickedBlock(), interactEvent.getBlockFace(), interactEvent.getHand(),
                        interactEvent.getClickedPosition());
					this.eventService.callEvent(physicalInteractEvent);
					interactEvent.setCancelled(physicalInteractEvent.isCancelled());
			}
		});
    }

    private void registerPlayerStatisticIncrementEvent() {
        this.eventService.register(PlayerStatisticIncrementEvent.class, event -> {
			final Statistic statistic = event.getStatistic();
			final Player player = event.getPlayer();
			Location location = player.getLocation();
			if (statistic == Statistic.JUMP) {
				this.eventService.callEvent(new PlayerJumpEvent(player, location, location.clone().add(player.getVelocity())));
			} else if (statistic == Statistic.DAMAGE_BLOCKED_BY_SHIELD) {
				final EntityDamageEvent lastDamageCause = player.getLastDamageCause();
				if (lastDamageCause == null) {
					return;
				}
				this.eventService.callEvent(new PlayerDamageBlockByShieldEvent(player, lastDamageCause.getEntity(), event.getNewValue() - event.getPreviousValue()));
			}
		});
    }

    private void registerInventoryClickEvent() {
        this.eventService.register(InventoryClickEvent.class, inventoryClickEvent -> {
		
		});
    }

    private void registerBlockDispenseArmorEvent() {
        this.eventService.register(BlockDispenseArmorEvent.class, event -> {
			final ArmorEquipEvent.ArmorType armorType = ArmorEquipEvent.ArmorType.matchType(event.getItem());
			if (armorType == null) {
				return;
			}
			final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(event.getTargetEntity(),
			                                                            ArmorEquipEvent.EquipMethod.DISPENSER, armorType,
			                                                            new ItemStack(Material.AIR), event.getItem());
			this.eventService.callEvent(armorEquipEvent);
			if (armorEquipEvent.isCancelled()) {
				event.setCancelled(true);
			}
		});
    }
}
