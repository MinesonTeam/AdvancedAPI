package kz.hxncus.mc.minesonapi.bukkit.inventory.marker;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class UnavailableItemMarker implements ItemMarker {
    @Override
    public ItemStack markItem(@NonNull ItemStack item) {
        return item;
    }

    @Override
    public ItemStack unmarkItem(@NonNull ItemStack item) {
        return item;
    }

    @Override
    public boolean isItemMarked(@NonNull ItemStack item) {
        return false;
    }
}
