package kz.hxncus.mc.advancedapi.bukkit.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * PlayerDamageBlockByShieldEvent
 */
public class PlayerDamageBlockByShieldEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private final Entity source;
	private boolean cancelled = false;
	@Setter
	@Getter
	private double damage;
	
	/**
	 * @param who    Player who blocked damage by shield
	 * @param source A source that tried to damage a player
	 * @param damage Damage amount that a source dealt
	 */
	public PlayerDamageBlockByShieldEvent(final Player who, final Entity source, final double damage) {
		super(who);
		this.source = source;
		this.damage = damage;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void setCancelled(final boolean cancel) {
		this.cancelled = cancel;
		if (cancel) {
			this.player.damage(this.damage, this.source);
			final PlayerInventory inventory = this.player.getInventory();
			final ItemStack item;
			if (inventory.getItemInMainHand()
			             .getType() == Material.SHIELD) {
				item = inventory.getItemInMainHand();
			} else if (inventory.getItemInOffHand()
			                    .getType() == Material.SHIELD) {
				item = inventory.getItemInOffHand();
			} else {
				return;
			}
			item.setDurability((short) (item.getDurability() + 1));
		}
	}
	
	@Override
	public @NonNull HandlerList getHandlers() {
		return handlers;
	}
}
