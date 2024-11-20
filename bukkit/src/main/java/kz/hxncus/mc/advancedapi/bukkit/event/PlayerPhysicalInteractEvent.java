package kz.hxncus.mc.advancedapi.bukkit.event;

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
public class PlayerPhysicalInteractEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();
	protected ItemStack item;
	protected Action action;
	protected Block blockClicked;
	protected BlockFace blockFace;
	private Result useClickedBlock;
	private Result useItemInHand;
	private EquipmentSlot hand;
	private Vector clickedPosition;
	
	public PlayerPhysicalInteractEvent(final Player who, final Action action, final ItemStack item, final Block clickedBlock, final BlockFace clickedFace, final EquipmentSlot hand, final Vector clickedPosition) {
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
	public boolean isCancelled() {
		return this.useClickedBlock == Result.DENY;
	}
	
	@Override
	public void setCancelled(final boolean cancel) {
		this.setUseClickedBlock(cancel ? Result.DENY : this.useClickedBlock == Result.DENY ? Result.DEFAULT : this.useClickedBlock);
		this.setUseItemInHand(cancel ? Result.DENY : this.useItemInHand == Result.DENY ? Result.DEFAULT : this.useItemInHand);
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
