package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

@UtilityClass
public class ArrowUtil {
    public static ItemStack getBow(Arrow arrow) {
        List<MetadataValue> values = arrow.getMetadata("shot-from");
        if (values.isEmpty()) {
            return null;
        }
        Object value = values.get(0).value();
        if (!(value instanceof ItemStack)) {
            return null;
        }
        return (ItemStack) value;
    }
}
