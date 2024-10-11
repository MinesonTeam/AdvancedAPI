package kz.hxncus.mc.advancedapi.utility.reflect;

import lombok.ToString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class Reflect method.
 *
 * @author Hxncus
 * @since 1.0.1
 */
@ToString
public class ReflectMethod {
	private final Class<?> clazz;
	
	private final String name;
	
	private final Class<?>[] parameterTypes;
	
	private Method method;
	
	/**
	 * Instantiates a new Reflection method.
	 *
	 * @param clazz          the clazz
	 * @param name           the name
	 * @param parameterTypes the parameter types
	 */
	public ReflectMethod(final Class<?> clazz, final String name, final Class<?>... parameterTypes) {
		this.clazz = clazz;
		this.name = name;
		this.parameterTypes = parameterTypes;
	}
	
	/**
	 * Invoke t.
	 *
	 * @param <T>      the type parameter
	 * @param instance the instance
	 * @param args     the args
	 * @return the t
	 */
	public <T> T invoke(final Object instance, final Object... args) {
		this.init();
		final Object object;
		try {
			object = this.method.invoke(instance, args);
		} catch (final IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return object == null ? null : (T) object;
	}
	
	private void init() {
		if (this.method == null) {
			try {
				try {
					this.method = this.clazz.getDeclaredMethod(this.name, this.parameterTypes);
				} catch (final NoSuchMethodException e) {
					this.method = this.clazz.getMethod(this.name, this.parameterTypes);
				}
				this.method.setAccessible(true);
			} catch (final NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Invoke static t.
	 *
	 * @param <T>  the type parameter
	 * @param args the args
	 * @return the t
	 */
	public <T> T invokeStatic(final Object... args) {
		this.init();
		final Object object;
		try {
			object = this.method.invoke(null, args);
		} catch (final IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return object == null ? null : (T) object;
	}
}

