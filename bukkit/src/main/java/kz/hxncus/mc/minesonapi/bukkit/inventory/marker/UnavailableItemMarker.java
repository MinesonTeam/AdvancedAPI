package kz.hxncus.mc.minesonapi.bukkit.inventory.marker;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class UnavailableItemMarker implements ItemMarker {
	@Override
	public ItemStack markItem(@NonNull final ItemStack item) {
		return item;
	}
	
	@Override
	public ItemStack unmarkItem(@NonNull final ItemStack item) {
		return item;
	}
	
	@Override
	public boolean isItemMarked(@NonNull final ItemStack item) {
		return false;
	}
}
