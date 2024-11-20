package kz.hxncus.mc.advancedapi.api.bukkit.config.property;

import kz.hxncus.mc.advancedapi.api.bukkit.config.Config;
import lombok.Getter;

@Getter
public abstract class ConfigProperty<T> extends AbstractProperty<T> {
	private final Config config;
	
	protected ConfigProperty(final Config config, final String path, final T defaultValue) {
		super(path, defaultValue);
		this.config = config;
	}
	
	protected ConfigProperty(final Config config, final String path) {
		this(config, path, null);
	}
	
	public void setValue(T value) {
		this.getConfig().set(this.getPath(), value);
	}
}
