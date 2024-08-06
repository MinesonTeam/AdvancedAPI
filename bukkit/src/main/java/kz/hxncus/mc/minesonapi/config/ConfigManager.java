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
	
	public ConfigManager(final Plugin plugin) {
		ConfigManager.plugin = plugin;
	}
	
	public SimpleConfig getOrCreateConfig(final String name) {
		return this.stringConfigMap.computeIfAbsent(name, nameFunc -> new SimpleConfig(plugin.getDataFolder(), nameFunc));
	}
	
	public SimpleConfig getOrCreateConfig(final String parent, final String child) {
		return this.stringConfigMap.computeIfAbsent(child, nameFunc -> new SimpleConfig(plugin.getDataFolder() + parent, nameFunc));
	}
}
