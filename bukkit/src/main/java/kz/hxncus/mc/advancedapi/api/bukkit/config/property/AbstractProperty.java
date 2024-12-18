package kz.hxncus.mc.advancedapi.api.bukkit.config.property;

import lombok.NonNull;

public abstract class AbstractProperty<T> implements Property<T> {
	private final String path;
	private final T defaultValue;
	
	protected AbstractProperty(final String path, @NonNull final T defaultValue) {
		this.path = path;
		this.defaultValue = defaultValue;
	}
	
	protected AbstractProperty(final String path) {
		this(path, null);
	}
	
	@Override
	public String getPath() {
		return this.path;
	}
	
	@Override
	public T getDefaultValue() {
		return this.defaultValue;
	}
}
