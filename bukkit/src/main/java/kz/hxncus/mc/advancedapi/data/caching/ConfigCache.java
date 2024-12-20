package kz.hxncus.mc.advancedapi.data.caching;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

@Getter
public class ConfigCache<K, V extends ConfigurationSerializable> extends AdvancedCache<K, V> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigCache.class);

	private final String path;
	private final ConfigurationSection section;
    private final Function<K, String> keyConverter;
	private final Function<String, K> stringConverter;
	private final Function<ConfigurationSection, V> sectionConverter;

    public ConfigCache(final String path, final ConfigurationSection section, final Function<K, String> keyConverter,
			final Function<String, K> stringConverter, final Function<ConfigurationSection, V> sectionConverter) {
		super();
		this.path = path;
		this.section = section;
		this.keyConverter = keyConverter;
		this.stringConverter = stringConverter;
		this.sectionConverter = sectionConverter;
	}
	
	public ConfigCache(final long expireAfterAccessNanos, final long expireAfterWriteNanos,
			final String path, final ConfigurationSection section, final Function<K, String> keyConverter,
			final Function<String, K> stringConverter, final Function<ConfigurationSection, V> sectionConverter) {
		super(expireAfterAccessNanos, expireAfterWriteNanos);
		this.path = path;
		this.section = section;
		this.keyConverter = keyConverter;
		this.stringConverter = stringConverter;
		this.sectionConverter = sectionConverter;
	}
	
	public ConfigCache(final long expireAfterAccess, final long expireAfterWrite, TimeUnit timeUnit, 
			final String path, final ConfigurationSection section, final Function<K, String> keyConverter,
			final Function<String, K> stringConverter, final Function<ConfigurationSection, V> sectionConverter) {
		super(expireAfterAccess, expireAfterWrite, timeUnit);
		this.path = path;
		this.section = section;
		this.keyConverter = keyConverter;
		this.stringConverter = stringConverter;
		this.sectionConverter = sectionConverter;
	}

    public V loadFromConfig(final K key) {
		ConfigurationSection section = this.getSection();
		ConfigurationSection subSection = section.getConfigurationSection(this.getPath() + "." + key.toString());
        final V valueConverted = subSection == null ? null : this.getSectionConverter().apply(subSection);
        if (valueConverted == null) {
            return null;
        }
        this.put(key, valueConverted);
		return valueConverted;
	}

	public void loadAllFromConfig() {
		String path = this.getPath();
		ConfigurationSection section = this.getSection();
		ConfigurationSection subSection = section.getConfigurationSection(path);
		if (subSection == null) {
			subSection = section.createSection(path);
		}
		for (String key : subSection.getKeys(false)) {
			ConfigurationSection keySection = subSection.getConfigurationSection(key);
			if (keySection == null) {
				continue;
			}

			final K keyConverted = this.getStringConverter().apply(key);
			final V valueConverted = this.getSectionConverter().apply(keySection);
			
			if (keyConverted == null || valueConverted == null) {
				ConfigCache.LOGGER.error("Failed to load cache from the config: " + path + " key: " + key);
				continue;
			}
			
			this.put(keyConverted, valueConverted);
		}
	}
	
	public boolean storeToConfig(final K key) {
		V value = this.get(key);
		String path = this.getPath();
		ConfigurationSection section = this.getSection();
		String keyConverted = this.getKeyConverter().apply(key);
		String keyPath = path + "." + keyConverted;
		if (value == null) {
			section.set(keyPath, null);
			return false;
		}
		ConfigurationSection subSection = section.getConfigurationSection(keyPath);
		if (subSection == null) {
			subSection = section.createSection(keyPath);
		}
		subSection.set(keyConverted, value.serialize());
		return true;
	}

	public void storeAllToConfig() {
		String path = this.getPath();
		ConfigurationSection section = this.getSection();
		ConfigurationSection subSection = section.getConfigurationSection(path);
		if (subSection == null) {
			subSection = section.createSection(path);
		}
		for (final Entry<K, V> entry : this.entrySet()) {
			K key = entry.getKey();
			V value = entry.getValue();
			String keyConverted = this.getKeyConverter().apply(key);
			subSection.set(keyConverted, value.serialize());
		}
	}
}
