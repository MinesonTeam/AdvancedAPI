package kz.hxncus.mc.advancedapi.api.bukkit.inventory.marker;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public interface ItemMarker {
	ItemStack markItem(@NonNull ItemStack item);
	
	ItemStack unmarkItem(@NonNull ItemStack item);
	
	boolean isItemMarked(@NonNull ItemStack item);
}
