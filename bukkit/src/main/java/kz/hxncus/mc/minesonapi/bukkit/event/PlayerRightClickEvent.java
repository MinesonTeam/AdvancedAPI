package kz.hxncus.mc.minesonapi.bukkit.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

@Getter
@Setter
public class PlayerRightClickEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    protected ItemStack item;
    protected Action action;
    protected Block blockClicked;
    protected BlockFace blockFace;
    private Result useClickedBlock;
    private Result useItemInHand;
    private EquipmentSlot hand;
    private Vector clickedPosition;

    public PlayerRightClickEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace, EquipmentSlot hand, Vector clickedPosition) {
        super(who);
        this.action = action;
        this.item = item;
        this.blockClicked = clickedBlock;
        this.blockFace = clickedFace;
        this.hand = hand;
        this.clickedPosition = clickedPosition;

        this.useItemInHand = Result.DEFAULT;
        this.useClickedBlock = clickedBlock == null ? Result.DENY : Result.ALLOW;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return getUseClickedBlock() == Result.DENY;
    }

    @Override
    public void setCancelled(boolean cancel) {
        setUseClickedBlock(cancel ? Result.DENY : getUseClickedBlock() == Result.DENY ? Result.DEFAULT : getUseClickedBlock());
        setUseItemInHand(cancel ? Result.DENY : getUseItemInHand() == Result.DENY ? Result.DEFAULT : getUseItemInHand());
    }
}
