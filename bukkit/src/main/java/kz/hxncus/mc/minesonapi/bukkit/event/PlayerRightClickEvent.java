package kz.hxncus.mc.minesonapi.bukkit.event;

import lombok.Getter;
import lombok.NonNull;
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

import javax.annotation.Nullable;

@Getter
@Setter
public class PlayerRightClickEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    @Nullable
    protected ItemStack item;
    @NonNull
    protected Action action;
    @Nullable
    protected Block blockClicked;
    @NonNull
    protected BlockFace blockFace;
    @NonNull
    private Result useClickedBlock;
    @NonNull
    private Result useItemInHand;
    @Nullable
    private EquipmentSlot hand;
    @Nullable
    private Vector clickedPosition;

    public PlayerRightClickEvent(@NonNull Player who, @NonNull Action action, @Nullable ItemStack item, @Nullable Block clickedBlock, @NonNull BlockFace clickedFace, @Nullable EquipmentSlot hand, @Nullable Vector clickedPosition) {
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
