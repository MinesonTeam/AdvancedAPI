package kz.hxncus.mc.advancedapi.bukkit.config.property;

import kz.hxncus.mc.advancedapi.api.bukkit.config.property.Property;
import kz.hxncus.mc.advancedapi.bukkit.config.AdvancedConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

@UtilityClass
public class PropertyInitializer {
	public Property<String> of(final AdvancedConfig config, final String path, final String defaultValue) {
		return new StringProperty(config, path, defaultValue);
	}
	
	public Property<List<String>> of(final AdvancedConfig config, final String path, final List<String> defaultValue) {
		return new StringListProperty(config, path, defaultValue);
	}
	
	public Property<ConfigurationSection> of(final AdvancedConfig config, final String path, final ConfigurationSection defaultValue) {
		return new ConfigSectionProperty(config, path, defaultValue);
	}
}
