package kz.hxncus.mc.minesonapi.bukkit.inventory.marker;

import kz.hxncus.mc.minesonapi.bukkit.item.SimpleItem;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PDCItemMarker implements ItemMarker {
    private final NamespacedKey mark;

    public PDCItemMarker(@NonNull Plugin plugin) {
        this.mark = new NamespacedKey(plugin, "MinesonAPI");
    }

    @Override
    public ItemStack markItem(@NonNull ItemStack itemStack) {
        return new SimpleItem(itemStack).setPDC(this.mark,PersistentDataType.BYTE, (byte) 1).apply();
    }

    @Override
    public ItemStack unmarkItem(@NonNull ItemStack itemStack) {
        return new SimpleItem(itemStack).removePDC(this.mark).apply();
    }

    @Override
    public boolean isItemMarked(@NonNull ItemStack itemStack) {
        return new SimpleItem(itemStack).hasPDC(this.mark, PersistentDataType.BYTE);
    }
}
