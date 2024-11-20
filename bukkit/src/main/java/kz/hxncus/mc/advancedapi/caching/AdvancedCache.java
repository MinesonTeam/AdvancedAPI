package kz.hxncus.mc.advancedapi.caching;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.api.caching.LocalCache;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class AdvancedCache<K, V> extends LocalCache<K, V> {
	public AdvancedCache() {
		super();
	}
	
	public AdvancedCache(final long expireAfterAccessNanos, final long expireAfterWriteNanos) {
		super(expireAfterAccessNanos, expireAfterWriteNanos);
	}
	
	public AdvancedCache(final long expireAfterAccess, final long expireAfterWrite, TimeUnit timeUnit) {
		super(expireAfterAccess, expireAfterWrite, timeUnit);
	}
	
	public void loadFromConfig(final FileConfiguration config, final String path,
						final Function<String, K> keyConverter, final Function<ConfigurationSection, V> sectionConverter) {
		final ConfigurationSection section = config.getConfigurationSection(path);
		if (section == null) {
			return;
		}
		for (String key : section.getKeys(false)) {
			K keyConverted = keyConverter.apply(key);
			V valueConverted = sectionConverter.apply(section.getConfigurationSection(key));
			
			if (keyConverted == null || valueConverted == null) {
				AdvancedAPI.getInstance().getLogger().severe("Failed to load cache from the config: " + path + " key: " + key);
				continue;
			}
			
			this.put(keyConverted, valueConverted);
		}
	}
	
	public void storeToConfig(final FileConfiguration config, final String path,
	                          final Function<K, String> keyConverter, final Function<V, Object> valueConverter) {
		final ConfigurationSection section;
		if (config.isConfigurationSection(path)) {
			section = config.getConfigurationSection(path);
		} else {
			section = config.createSection(path);
		}
		for (final Entry<K, V> entry : this.entrySet()) {
			section.set(keyConverter.apply(entry.getKey()), valueConverter.apply(entry.getValue()));
		}
	}
}
