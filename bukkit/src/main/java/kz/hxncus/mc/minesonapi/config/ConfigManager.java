package kz.hxncus.mc.minesonapi.config;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ConfigManager {
    private static Plugin plugin;
    private final Map<String, Config> stringConfigMap = new ConcurrentHashMap<>();

    public ConfigManager(Plugin plugin) {
        ConfigManager.plugin = plugin;
    }

    public Config getOrCreateConfig(String name) {
        return this.stringConfigMap.computeIfAbsent(name, nameFunc -> new Config(plugin.getDataFolder(), nameFunc));
    }
}
