package kz.hxncus.mc.minesonapi.bukkit.event;

import kz.hxncus.mc.minesonapi.util.VectorUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
@Setter
public class PlayerJumpEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private Location from;
	private Location to;
	
	public PlayerJumpEvent(final Player player, final Location from, final Location to) {
		super(player);
		this.from = from;
		this.to = to;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	@Override
	public void setCancelled(final boolean cancel) {
		this.setCancelled(cancel);
		if (cancel) {
			this.player.setVelocity(VectorUtil.ZERO_VECTOR);
		}
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
