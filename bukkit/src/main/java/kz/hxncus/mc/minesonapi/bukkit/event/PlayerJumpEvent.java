package kz.hxncus.mc.minesonapi.bukkit.event;

import kz.hxncus.mc.minesonapi.util.VectorUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerJumpEvent extends PlayerMoveEvent {
    public PlayerJumpEvent(Player player, Location from, Location to) {
        super(player, from, to);
    }

    @Override
    public void setCancelled(boolean cancel) {
        super.setCancelled(cancel);
        if (cancel) {
            player.setVelocity(VectorUtil.ZERO_VECTOR);
        }
    }
}
