package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;

@UtilityClass
public class NamespacedKeyUtil {
    public static NamespacedKey create(String key) {
        return new NamespacedKey(MinesonAPI.get(), key);
    }
}