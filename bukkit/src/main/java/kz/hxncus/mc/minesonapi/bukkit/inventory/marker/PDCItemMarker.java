package kz.hxncus.mc.minesonapi.bukkit.inventory.marker;

import kz.hxncus.mc.minesonapi.util.builder.ItemBuilder;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PDCItemMarker implements ItemMarker {
	private final NamespacedKey mark;
	
	public PDCItemMarker(@NonNull final Plugin plugin) {
		this.mark = new NamespacedKey(plugin, "MinesonAPI");
	}
	
	@Override
	public ItemStack markItem(@NonNull final ItemStack itemStack) {
		return new ItemBuilder(itemStack).setPDC(this.mark, PersistentDataType.BYTE, (byte) 1)
		                                 .build();
	}
	
	@Override
	public ItemStack unmarkItem(@NonNull final ItemStack itemStack) {
		return new ItemBuilder(itemStack).removePDC(this.mark)
		                                 .build();
	}
	
	@Override
	public boolean isItemMarked(@NonNull final ItemStack itemStack) {
		return new ItemBuilder(itemStack).hasPDC(this.mark, PersistentDataType.BYTE);
	}
}
