package kz.hxncus.mc.advancedapi.api.bukkit.config.property;

import lombok.NonNull;

public interface Property<T> {
    @NonNull String getPath();
	@NonNull T getDefaultValue();
	@NonNull T getValue();
	void setValue(@NonNull T value);
}
