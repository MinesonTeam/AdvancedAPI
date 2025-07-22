package kz.hxncus.mc.advancedapi.annotation.processor;

import kz.hxncus.mc.advancedapi.annotation.Config;
import kz.hxncus.mc.advancedapi.annotation.ConfigComment;
import kz.hxncus.mc.advancedapi.utility.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ConfigProcessor {
    private final List<BukkitTask> tasks = new ArrayList<>();
    private final List<Class<?>> configClasses = new ArrayList<>();

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
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigComment comment = clazz.getAnnotation(ConfigComment.class);
        if (comment != null) {
            config.options().setHeader(Arrays.asList(comment.value()));
        }
        loadFields(config, clazz, "");
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        configClasses.add(clazz);
    }

    public void enable(JavaPlugin plugin) {
        for (Class<?> clazz : configClasses) {
            Config configAnnotation = clazz.getAnnotation(Config.class);
            if (configAnnotation == null) {
                continue;
            }
            File file = new File(plugin.getDataFolder(), configAnnotation.fileName());
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            tasks.add(Bukkit.getScheduler().runTaskTimer(plugin, () -> loadFields(config, clazz, ""),
                    configAnnotation.reloadDelay(), configAnnotation.reloadInterval()));
        }
    }

    public void disable() {
        tasks.forEach(BukkitTask::cancel);
        tasks.clear();
        configClasses.clear();
    }

    public void loadFields(FileConfiguration config, Class<?> clazz, String path) {
        if (!path.isEmpty() && !config.contains(path)) {
            config.createSection(path);
        }
        setComments(config, clazz, path);
        for (Class<?> declaredClass : clazz.getDeclaredClasses()) {
            String simpleName = declaredClass.getSimpleName().toLowerCase();
            String newPath = path.isEmpty() ? simpleName :
                    path + "." + simpleName;
            loadFields(config, declaredClass, newPath);
        }
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName().toLowerCase(Locale.ROOT);
            String newPath = path.isEmpty() ? fieldName :
                    path + "." + fieldName;
            Object fieldValue = ReflectionUtil.getFieldValue(field, null);
            if (config.contains(newPath)) {
                Object configValue = getConfigValue(config, field, newPath);
                ReflectionUtil.setPrimitiveField(field, null, configValue == null ? fieldValue : configValue);
            } else {
                config.set(newPath, fieldValue);
                setComments(config, field, newPath);
            }
        }
    }

    public void setComments(FileConfiguration config, AnnotatedElement element, String path) {
        ConfigComment mainComment = element.getAnnotation(ConfigComment.class);
        if (mainComment != null) {
            config.setComments(path, Arrays.asList(mainComment.value()));
        }
    }

    public Object getConfigValue(FileConfiguration config, Field field, String path) {
        if (field.getType().isPrimitive()) {
            if (field.getType().equals(boolean.class)) {
                return config.getBoolean(path);
            } else if (field.getType().equals(int.class)) {
                return config.getInt(path);
            } else if (field.getType().equals(long.class)) {
                return config.getLong(path);
            } else if (field.getType().equals(double.class)) {
                return config.getDouble(path);
            }
        }
        return config.getObject(path, field.getType());
    }
}
