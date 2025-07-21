package kz.hxncus.mc.advancedapi.annotation.processor;

import kz.hxncus.mc.advancedapi.annotation.Config;
import kz.hxncus.mc.advancedapi.utility.ReflectionUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;

@UtilityClass
public class ConfigProcessor {
    public void process(JavaPlugin plugin, Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Config.class)) {
            return;
        }
        String filePath = clazz.getAnnotation(Config.class).value();
        File file = new File(plugin.getDataFolder(), filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            if (plugin.getResource(filePath) != null) {
                plugin.saveResource(filePath, false);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (Field field : clazz.getDeclaredFields()) {
            String path = field.getName().toLowerCase(Locale.ROOT);
            Object value = config.get(path);
            if (value != null) {
                ReflectionUtil.setField(field, null, value);
            }
        }
    }
}
