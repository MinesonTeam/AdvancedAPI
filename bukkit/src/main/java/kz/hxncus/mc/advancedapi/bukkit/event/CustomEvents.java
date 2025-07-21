package kz.hxncus.mc.advancedapi.bukkit.event;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;

public class CustomEvents {
	private static boolean isRegistered = false;
    private final EventDispatcher eventDispatcher;

    public CustomEvents(@NonNull EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    public void registerEvents() {
		if (CustomEvents.isRegistered) {
			return;
		}
		this.registerInventoryClickEvent();
		this.registerPlayerInteractEvent();
		this.registerPlayerStatisticIncrementEvent();
        this.registerBlockDispenseArmorEvent();

		CustomEvents.isRegistered = true;
    }

	// Add check for new version to add extra parameter interactEvent.getClickedPosition()
    private void registerPlayerInteractEvent() {
        this.eventDispatcher.register(PlayerInteractEvent.class, interactEvent -> {
			final Action action = interactEvent.getAction();
			switch (action) {
				case LEFT_CLICK_BLOCK:
				case LEFT_CLICK_AIR:
					PlayerLeftClickEvent leftClickEvent = new PlayerLeftClickEvent(interactEvent.getPlayer(), interactEvent.getAction(),
                        interactEvent.getItem(), interactEvent.getClickedBlock(), interactEvent.getBlockFace(), interactEvent.getHand());
					this.eventDispatcher.callEvent(leftClickEvent);
					break;
				case RIGHT_CLICK_BLOCK:
				case RIGHT_CLICK_AIR:
					PlayerRightClickEvent rightClickEvent = new PlayerRightClickEvent(interactEvent.getPlayer(), interactEvent.getAction(),
                        interactEvent.getItem(), interactEvent.getClickedBlock(), interactEvent.getBlockFace(), interactEvent.getHand());
					this.eventDispatcher.callEvent(rightClickEvent);
					break;
				case PHYSICAL:
					PlayerPhysicalInteractEvent physicalInteractEvent = new PlayerPhysicalInteractEvent(interactEvent.getPlayer(), interactEvent.getAction(),
                        interactEvent.getItem(), interactEvent.getClickedBlock(), interactEvent.getBlockFace(), interactEvent.getHand());
					this.eventDispatcher.callEvent(physicalInteractEvent);
			}
		});
    }

    private void registerPlayerStatisticIncrementEvent() {
        this.eventDispatcher.register(PlayerStatisticIncrementEvent.class, event -> {
			final Statistic statistic = event.getStatistic();
			final Player player = event.getPlayer();
			if (statistic == Statistic.JUMP) {
				this.eventDispatcher.callEvent(new PlayerJumpEvent(player, player.getLocation(), player.getLocation().clone().add(player.getVelocity())));
			}
		});
    }

    private void registerInventoryClickEvent() {
        this.eventDispatcher.register(InventoryClickEvent.class, inventoryClickEvent -> {
		
		});
    }

    private void registerBlockDispenseArmorEvent() {
        this.eventDispatcher.register(BlockDispenseArmorEvent.class, event -> {
			final ArmorEquipEvent.ArmorType armorType = ArmorEquipEvent.ArmorType.matchType(event.getItem());
			if (armorType == null) {
				return;
			}
			final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(event.getTargetEntity(),
			                                                            ArmorEquipEvent.EquipMethod.DISPENSER, armorType,
			                                                            new ItemStack(Material.AIR), event.getItem());
			this.eventDispatcher.callEvent(armorEquipEvent);
			if (armorEquipEvent.isCancelled()) {
				event.setCancelled(true);
			}
		});
    }
}
