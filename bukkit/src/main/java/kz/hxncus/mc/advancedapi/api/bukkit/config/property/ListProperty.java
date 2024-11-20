package kz.hxncus.mc.advancedapi.api.bukkit.config.property;

import kz.hxncus.mc.advancedapi.api.bukkit.config.Config;

import java.util.List;

public abstract class ListProperty<T> extends ConfigProperty<List<T>> {
	protected ListProperty(final Config config, final String path) {
		super(config, path);
	}
	
	protected ListProperty(final Config config, final String path, final List<T> defaultValue) {
		super(config, path, defaultValue);
	}
}
