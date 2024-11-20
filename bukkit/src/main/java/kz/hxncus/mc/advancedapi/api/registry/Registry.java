package kz.hxncus.mc.advancedapi.api.registry;

import lombok.NonNull;

import javax.annotation.Nullable;

public interface Registry<T> extends Iterable<T> {
	@Nullable
	T get(@NonNull String key);
}
