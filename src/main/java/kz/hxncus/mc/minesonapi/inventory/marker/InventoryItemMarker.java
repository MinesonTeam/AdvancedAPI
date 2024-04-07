package kz.hxncus.mc.minesonapi.inventory.marker;

import kz.hxncus.mc.minesonapi.util.ItemBuilder;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class InventoryItemMarker {
    private final NamespacedKey mark;

    public InventoryItemMarker(@NonNull Plugin plugin, @NonNull String mark) {
        this.mark = new NamespacedKey(plugin, mark);
    }

    @NonNull
    public ItemStack markItem(@NonNull ItemStack itemStack) {
        return new ItemBuilder(itemStack).meta(meta -> meta.getPersistentDataContainer().set(this.mark, PersistentDataType.BYTE, (byte) 1)).build();
    }

    @NonNull
    public ItemStack unMarkItem(@NonNull ItemStack itemStack) {
        return new ItemBuilder(itemStack).meta(meta -> meta.getPersistentDataContainer().remove(this.mark)).build();
    }

    public boolean isItemMarked(@NonNull ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null)
            return false;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        return container.has(this.mark, PersistentDataType.BYTE);
    }
}
