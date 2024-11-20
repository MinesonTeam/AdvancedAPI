package kz.hxncus.mc.advancedapi.bukkit.config.property;

import kz.hxncus.mc.advancedapi.api.bukkit.config.Config;
import kz.hxncus.mc.advancedapi.api.bukkit.config.property.ConfigProperty;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigSectionProperty extends ConfigProperty<ConfigurationSection> {
	public ConfigSectionProperty(final Config config, final String path) {
		super(config, path);
	}
	
	public ConfigSectionProperty(final Config config, final String path, final ConfigurationSection defaultValue) {
		super(config, path, defaultValue);
	}
	
	@Override
	public ConfigurationSection getValue() {
		Config config = this.getConfig();
		String path = this.getPath();
		if (!config.contains(path)) {
			return this.getDefaultValue();
		}
		ConfigurationSection section = config.getConfigurationSection(path);
		if (section == null) {
			return this.getDefaultValue();
		}
		return section;
	}
}
