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
	
	public PlayerRightClickEvent(@NonNull final Player who, @NonNull final Action action, @Nullable final ItemStack item, @Nullable final Block clickedBlock, @NonNull final BlockFace clickedFace, @Nullable final EquipmentSlot hand, @Nullable final Vector clickedPosition) {
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
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	@Override
	public boolean isCancelled() {
		return this.useClickedBlock == Result.DENY;
	}
	
	@Override
	public void setCancelled(final boolean cancel) {
		this.setUseClickedBlock(cancel ? Result.DENY : this.useClickedBlock == Result.DENY ? Result.DEFAULT : this.useClickedBlock);
		this.setUseItemInHand(cancel ? Result.DENY : this.useItemInHand == Result.DENY ? Result.DEFAULT : this.useItemInHand);
	}
}
