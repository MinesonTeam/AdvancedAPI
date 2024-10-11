package kz.hxncus.mc.advancedapi.bukkit.event;

import kz.hxncus.mc.advancedapi.utility.VectorUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
@Setter
@ToString
public class PlayerJumpEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private Location from;
	private Location to;
	
	public PlayerJumpEvent(final Player who, final Location from, final Location to) {
		super(who);
		this.from = from;
		this.to = to;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	@Override
	public void setCancelled(final boolean cancel) {
		this.cancelled = cancel;
		if (cancel) {
			this.player.setVelocity(VectorUtil.ZERO_VECTOR);
		}
	}
	
	@Override
	public @NonNull HandlerList getHandlers() {
		return handlers;
	}
}
