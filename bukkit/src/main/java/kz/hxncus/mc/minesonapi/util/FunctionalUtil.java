package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

@UtilityClass
public class FunctionalUtil {
	public <T> boolean acceptIfTrue(final BooleanSupplier runnable, final Consumer<T> consumer, final T type) {
		final boolean bool = runnable.getAsBoolean();
		if (bool) {
			consumer.accept(type);
		}
		return bool;
	}
	
	@Nullable
	public <T, R> R applyIfTrue(final BooleanSupplier runnable, final Function<T, R> function, final T type) {
		if (runnable.getAsBoolean()) {
			return function.apply(type);
		}
		return null;
	}
	
	public <T> boolean acceptIfFalse(final BooleanSupplier runnable, final Consumer<T> consumer, final T type) {
		final boolean bool = !runnable.getAsBoolean();
		if (bool) {
			consumer.accept(type);
		}
		return bool;
	}
	
	@Nullable
	public <T, R> R applyIfFalse(final BooleanSupplier runnable, final Function<T, R> function, final T type) {
		if (!runnable.getAsBoolean()) {
			return function.apply(type);
		}
		return null;
	}
}
