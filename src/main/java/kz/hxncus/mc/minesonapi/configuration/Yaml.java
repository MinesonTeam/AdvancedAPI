package kz.hxncus.mc.minesonapi.configuration;

import kz.hxncus.mc.minesonapi.util.ReflectionUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.NumberConversions;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class Yaml {
    protected static final Map<String, Yaml> YAML_MAP = new HashMap<>();
    @Getter
    private final Plugin plugin;
    @Getter
    private final File file;
    private YamlConfiguration config;
    public Yaml(@NonNull Plugin plugin, @NonNull String resource) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), resource);
        createFile(StringUtils.removeEnd(resource, ".yml"), Yaml.class.getClassLoader().getResourceAsStream(resource));
    }

    public static void removeYamls(Plugin plugin) {
        YAML_MAP.entrySet().stream().filter(entry -> entry.getValue().getPlugin() == plugin).forEach(entry -> {
            entry.getValue().reloadConfig();
            YAML_MAP.remove(entry.getKey());
        });
    }

    public void reload(boolean fromDisk) {
        if (fromDisk) {
            this.reloadConfig();
        } else {
            this.saveConfig();
        }
    }

    @Nullable
    public static Yaml getYAML(String name) {
        Yaml yaml = YAML_MAP.get(name);
        if (yaml != null) {
            yaml.reloadConfig();
        }
        return yaml;
    }

    private void createFile(String name, @Nullable InputStream stream) {
        try {
            if (this.file.getParentFile() != null && !this.file.getParentFile().exists())
                Files.createDirectories(this.file.getParentFile().toPath());
            if (!this.file.exists()) {
                if (stream == null) {
                    throw new RuntimeException("File and stream is null: " + name);
                }
                Files.copy(stream, this.file.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();
        YAML_MAP.put(name, this);
    }

    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
        final InputStream defConfigStream = plugin.getResource(this.file.getName());
        if (defConfigStream == null) {
            return;
        }
        getConfig().setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, StandardCharsets.UTF_8)));
    }

    public void saveConfig() {
        try {
            getConfig().save(this.file);
        } catch (IOException ignored) {
            // ignored
        }
    }

    public void saveDefaultConfig() {
        if (!this.file.exists()) {
            plugin.saveResource(this.file.getName(), false);
        }
    }

    @NonNull
    public FileConfiguration getConfig() {
        if (this.config == null) {
            reloadConfig();
        }
        return this.config;
    }

    public void setObject(@NonNull String path, @NonNull Object object) {
        getConfig().set(path, object);
    }

    public <C> void setClassList(@NonNull String path, @NonNull Constructor<C> constructor, @NonNull Object @NonNull ... objects) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object object : objects) {
            Class<C> clazz = constructor.getDeclaringClass();
            if (ReflectionUtil.getDeclaredConstructor(clazz) == null) {
                throw new IllegalArgumentException("Class must have a empty constructor");
            }
            list.add(getClassMap(clazz.getDeclaredFields(), constructor.getParameterTypes(), object));
        }
        getConfig().set(path, list);
    }

    public <C> void setClass(@NonNull String path, @NonNull Object object, @NonNull Constructor<C> constructor) {
        Class<C> clazz = constructor.getDeclaringClass();
        if (ReflectionUtil.getDeclaredConstructor(clazz) == null) {
            throw new IllegalArgumentException("Class must have a empty constructor");
        }
        getConfig().set(path, getClassMap(clazz.getDeclaredFields(), constructor.getParameterTypes(), object));
    }

    @NonNull
    private Map<String, Object> getClassMap(@NonNull Field[] fields, @NonNull Class<?>[] parameterTypes, @NonNull Object object) {
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        for (Field field : fields) {
            if (i >= parameterTypes.length) {
                break;
            }
            field.setAccessible(true);
            Object value = ReflectionUtil.getFieldValue(field, object);
            if (value == null || parameterTypes[i] != field.getType()) {
                continue;
            }
            i++;
            map.put(field.getName(), value);
        }
        return map;
    }
    @NonNull
    public <C> Object newClassInstance(@NonNull Constructor<C> constructor, Map map) {
        Class<C> clazz = constructor.getDeclaringClass();
        return ReflectionUtil.newInstance(constructor, Arrays.stream(clazz.getDeclaredFields()).map(field -> map.get(field.getName()))
                                                             .filter(Objects::nonNull).toArray(Object[]::new));
    }
    @Nullable
    public <C> Object getClass(@NonNull String path, @NonNull Constructor<C> constructor) {
        Map<String, Object> map = (Map) getConfig().get(path);
        if (map == null || map.isEmpty()) {
            return null;
        }
        return newClassInstance(constructor, map);
    }
    public <C> List<Object> getClassList(@NonNull String path, @NonNull Constructor<C> constructor) {
        List<?> list = getList(path, null);
        if (list == null) {
            return Collections.emptyList();
        }
        List<Object> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Map map) {
                result.add(newClassInstance(constructor, map));
            }
        }
        return result;
    }
    @Nullable
    public Object getObject(@NonNull String path, @Nullable Object def) {
        return getConfig().get(path, def);
    }

    @Nullable
    public List<?> getList(@NonNull String path, @Nullable List<?> def) {
        Object val = getObject(path, def);
        return (List<?>) ((val instanceof List) ? val : def);
    }

    @Nullable
    public String getString(@NonNull String path, @Nullable String string) {
        return getConfig().getString(path, string);
    }

    @Nullable
    public List<String> getStringList(@NonNull String path) {
        return getConfig().getStringList(path);
    }
    public List<Long> getLongList(@NonNull String path) {
        return getConfig().getLongList(path);
    }
    public long getLong(@NonNull String path) {
        return getConfig().getLong(path);
    }

    public long getLong(@NonNull String path, long def) {
        return getConfig().getLong(path, def);
    }
    public List<Float> getFloatList(@NonNull String path) {
        List<?> list = getList(path, null);
        if (list == null) {
            return Collections.emptyList();
        }
        List<Float> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number number) {
                result.add(NumberConversions.toFloat(number));
            }
        }
        return result;
    }
    public float getFloat(@NonNull String path) {
        Object def = getObject(path, null);
        return getFloat(path, (def instanceof Number) ? NumberConversions.toFloat(def) : 0);
    }

    public float getFloat(@NonNull String path, float def) {
        Object value = getObject(path, def);
        return (value instanceof Number) ? NumberConversions.toFloat(value) : def;
    }

    @NonNull
    public Set<String> getKeys(boolean deep) {
        return getConfig().getKeys(deep);
    }

    @NonNull
    public ConfigurationSection getSection(@NonNull String path) {
        ConfigurationSection section = getConfig().getConfigurationSection(path);
        return section == null ? getConfig().createSection(path) : section;
    }
}
