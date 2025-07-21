package kz.hxncus.mc.advancedapi.annotation.processor;

import kz.hxncus.mc.advancedapi.annotation.Hologram;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;

@UtilityClass
public class HologramProcessor {
    public void process(JavaPlugin plugin, Class<?> clazz, Object obj) {
        Hologram hologram = clazz.getAnnotation(Hologram.class);
        if (hologram == null) {
            return;
        }

    }
}
