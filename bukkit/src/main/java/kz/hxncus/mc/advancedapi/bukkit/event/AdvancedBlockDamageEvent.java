package kz.hxncus.mc.advancedapi.bukkit.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class AdvancedBlockDamageEvent extends BlockDamageEvent {
	public AdvancedBlockDamageEvent(final Player player, final Block block, final ItemStack itemInHand, final boolean instaBreak) {
		super(player, block, itemInHand, instaBreak);
	}
}
