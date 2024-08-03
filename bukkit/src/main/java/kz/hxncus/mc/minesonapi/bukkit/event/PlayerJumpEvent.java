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
    private boolean cancel = false;
    private Location from;
    private Location to;

    public PlayerJumpEvent(Player player, Location from, Location to) {
        super(player);
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        setCancel(cancel);
        if (cancel) {
            player.setVelocity(VectorUtil.ZERO_VECTOR);
        }
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
