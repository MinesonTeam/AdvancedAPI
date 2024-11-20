package kz.hxncus.mc.advancedapi.api.bukkit.config.property;

import lombok.NonNull;

import javax.annotation.Nullable;

public interface Property<T> {
    @NonNull
	String getPath();
	@Nullable
	T getDefaultValue();
	@Nullable
	T getValue();
	void setValue(T value);
}
