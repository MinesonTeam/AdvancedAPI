package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class Functional util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public class FunctionalUtil {
	/**
	 * Accept if true boolean.
	 *
	 * @param <T>      the type parameter
	 * @param runnable the runnable
	 * @param consumer the consumer
	 * @param type     the type
	 * @return the boolean
	 */
	public <T> boolean ifTrueAccept(final BooleanSupplier runnable, final Consumer<T> consumer, final T type) {
		final boolean willAccept = runnable.getAsBoolean();
		if (willAccept) {
			consumer.accept(type);
		}
		return willAccept;
	}
	
	/**
	 * Apply if true r.
	 *
	 * @param <T>      the type parameter
	 * @param <R>      the type parameter
	 * @param runnable the runnable
	 * @param function the function
	 * @param type     the type
	 * @return the r
	 */
	@Nullable
	public <T, R> R ifTrueApply(final BooleanSupplier runnable, final Function<T, R> function, final T type) {
		if (runnable.getAsBoolean()) {
			return function.apply(type);
		}
		return null;
	}
	
	/**
	 * Accept if false boolean.
	 *
	 * @param <T>      the type parameter
	 * @param runnable the runnable
	 * @param consumer the consumer
	 * @param type     the type
	 * @return the boolean
	 */
	public <T> boolean ifFalseAccept(final BooleanSupplier runnable, final Consumer<T> consumer, final T type) {
		final boolean willAccept = !runnable.getAsBoolean();
		if (willAccept) {
			consumer.accept(type);
		}
		return willAccept;
	}
	
	/**
	 * Apply if false r.
	 *
	 * @param <T>      the type parameter
	 * @param <R>      the type parameter
	 * @param runnable the runnable
	 * @param function the function
	 * @param type     the type
	 * @return the r
	 */
	@Nullable
	public <T, R> R ifFalseApply(final BooleanSupplier runnable, final Function<T, R> function, final T type) {
		if (!runnable.getAsBoolean()) {
			return function.apply(type);
		}
		return null;
	}
}
