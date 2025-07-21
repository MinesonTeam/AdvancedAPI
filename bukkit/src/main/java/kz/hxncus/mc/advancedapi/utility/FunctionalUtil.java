package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class Functional util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public final class FunctionalUtil {
	/**
	 * Accept if true boolean.
	 *
	 * @param <T>      the type parameter
	 * @param willAccept the willAccept
	 * @param consumer the consumer
	 * @param type     the type
     */
	public <T> void acceptIfTrue(final boolean willAccept, final Consumer<T> consumer, final T type) {
		if (willAccept) {
			consumer.accept(type);
		}
	}
	
	/**
	 * Apply if true r.
	 *
	 * @param <T>      the type parameter
	 * @param <R>      the type parameter
	 * @param willApply the willApply
	 * @param function the function
	 * @param type     the type
	 * @return the r
	 */
	public <T, R> R applyIfTrue(final boolean willApply, final Function<T, R> function, final T type) {
		if (willApply) {
			return function.apply(type);
		}
		return null;
	}
	
	/**
	 * Accept if false boolean.
	 *
	 * @param <T>      the type parameter
	 * @param willNotAccept    the willNotAccept
	 * @param consumer the consumer
	 * @param type     the type
     */
	public <T> void acceptIfFalse(final boolean willNotAccept, final Consumer<T> consumer, final T type) {
		if (!willNotAccept) {
			consumer.accept(type);
		}
	}
	
	/**
	 * Apply if false r.
	 *
	 * @param <T>      the type parameter
	 * @param <R>      the type parameter
	 * @param willNotApply    it willNotApply
	 * @param function the function
	 * @param type     the type
	 * @return the r
	 */
	public <T, R> R applyIfFalse(final boolean willNotApply, final Function<T, R> function, final T type) {
		if (!willNotApply) {
			return function.apply(type);
		}
		return null;
	}
}
