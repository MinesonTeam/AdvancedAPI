package kz.hxncus.mc.advancedapi.bukkit.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class Config manager.
 *
 * @author Hxncus
 * @since 1.0.0
 */
@Getter
@ToString
@EqualsAndHashCode
public class ConfigService {
	private static Plugin plugin;
	private final Map<String, SimpleConfig> stringConfigMap = new ConcurrentHashMap<>(8);
	
	/**
	 * Instantiates a new Config manager.
	 *
	 * @param plugin the plugin
	 */
	public ConfigService(final Plugin plugin) {
		ConfigService.plugin = plugin;
	}
	
	/**
	 * Gets or create config.
	 *
	 * @param name the name
	 * @return the or creation config
	 */
	public SimpleConfig getOrCreateConfig(final String name) {
		return this.stringConfigMap.computeIfAbsent(name, nameFunc -> new SimpleConfig(plugin.getDataFolder(), nameFunc));
	}
	
	/**
	 * Gets or create config.
	 *
	 * @param parent the parent
	 * @param child  the child
	 * @return the or creation config
	 */
	public SimpleConfig getOrCreateConfig(final String parent, final String child) {
		return this.stringConfigMap.computeIfAbsent(child, nameFunc -> new SimpleConfig(plugin.getDataFolder() + parent, nameFunc));
	}
}
