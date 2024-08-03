package kz.hxncus.mc.minesonapi.bukkit.event;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PlayerPhysicalInteractEvent extends PlayerInteractEvent {
    public PlayerPhysicalInteractEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace, EquipmentSlot hand, Vector clickedPosition) {
        super(who, action, item, clickedBlock, clickedFace, hand, clickedPosition);
    }
}
