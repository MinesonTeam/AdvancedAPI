package kz.hxncus.mc.advancedapi.bukkit.config.property;

import kz.hxncus.mc.advancedapi.api.bukkit.config.Config;
import kz.hxncus.mc.advancedapi.api.bukkit.config.property.ConfigProperty;

public class StringProperty extends ConfigProperty<String> {
	public StringProperty(final Config config, final String path) {
		super(config, path);
	}
	
	public StringProperty(final Config config, final String path, final String defaultValue) {
		super(config, path, defaultValue);
	}
	
	@Override
	public String getValue() {
		return this.getConfig().getString(this.getPath(), this.getDefaultValue());
	}
}
