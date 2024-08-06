package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

import javax.annotation.Nullable;
import java.util.List;

/**
 * ArrowUtil
 */
@UtilityClass
public class ArrowUtil {
	/**
	 * Get Bow from Metadatable shot Arrow
	 *
	 * @param metadatable Arrow
	 * @return Bow ItemStack
	 */
	@Nullable
	public ItemStack getBowFromArrow(final Metadatable metadatable) {
		final List<MetadataValue> values = metadatable.getMetadata("shot-from");
		final Object value = values.getFirst()
		                           .value();
		if (!(value instanceof ItemStack)) {
			return null;
		}
		return (ItemStack) value;
	}
}
