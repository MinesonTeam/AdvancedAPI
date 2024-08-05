package kz.hxncus.mc.minesonapi.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@EqualsAndHashCode
public class ConfigManager {
    private static Plugin plugin;
    private final Map<String, SimpleConfig> stringConfigMap = new ConcurrentHashMap<>();

    public ConfigManager(Plugin plugin) {
        ConfigManager.plugin = plugin;
    }

    public SimpleConfig getOrCreateConfig(String name) {
        return this.stringConfigMap.computeIfAbsent(name, nameFunc -> new SimpleConfig(plugin.getDataFolder(), nameFunc));
    }

    public SimpleConfig getOrCreateConfig(String parent, String child) {
        return this.stringConfigMap.computeIfAbsent(child, nameFunc -> new SimpleConfig(plugin.getDataFolder() + parent, nameFunc));
    }
}
