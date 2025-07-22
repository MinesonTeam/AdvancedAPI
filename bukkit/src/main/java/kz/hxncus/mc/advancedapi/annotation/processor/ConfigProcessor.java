package kz.hxncus.mc.advancedapi.annotation.processor;

import kz.hxncus.mc.advancedapi.annotation.Config;
import kz.hxncus.mc.advancedapi.annotation.ConfigComment;
import kz.hxncus.mc.advancedapi.utility.ReflectionUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;

@UtilityClass
public class ConfigProcessor {

    public void process(JavaPlugin plugin, Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Config.class)) {
            return;
        }
        Config configAnnotation = clazz.getAnnotation(Config.class);
        String fileName = configAnnotation.fileName();
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ConfigComment comment = clazz.getAnnotation(ConfigComment.class);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (comment != null) {
            config.setComments("", Arrays.asList(comment.value()));
        }
        loadFields(config, clazz, "");
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getScheduler().runTaskTimer(plugin, () -> loadFields(config, clazz, ""),
                configAnnotation.reloadDelay(), configAnnotation.reloadInterval());
    }

    public void loadFields(FileConfiguration config, Class<?> clazz, String path) {
        for (Class<?> declaredClass : clazz.getDeclaredClasses()) {
            loadFields(config, declaredClass, path.isEmpty() ? declaredClass.getName() : path + "." + declaredClass.getName());
        }
        for (Field field : clazz.getDeclaredFields()) {
            String newPath = path + field.getName().toLowerCase(Locale.ROOT);
            if (!config.contains(newPath)) {
                ReflectionUtil.setField(field, null, config.get(newPath));
            } else {
                ConfigComment comment = field.getAnnotation(ConfigComment.class);
                if (comment != null) {
                    config.setComments(newPath, Arrays.asList(comment.value()));
                }
                config.set(newPath, ReflectionUtil.getFieldValue(field, null));
            }
        }
    }
}
