package kz.hxncus.mc.minesonapi.bukkit.event;

import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerDamageBlockByShieldEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private final Entity source;
    @Setter
    private double damage;

    public PlayerDamageBlockByShieldEvent(Player who, Entity source, double damage) {
        super(who);
        this.source = source;
        this.damage = damage;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
        if (cancel) {
            player.damage(damage, source);
            PlayerInventory inventory = player.getInventory();
            ItemStack item;
            if (inventory.getItemInMainHand().getType() == Material.SHIELD) {
                item = inventory.getItemInMainHand();
            } else if (inventory.getItemInOffHand().getType() == Material.SHIELD) {
                item = inventory.getItemInOffHand();
            } else {
                return;
            }
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
