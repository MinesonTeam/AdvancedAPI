package kz.hxncus.mc.minesonapi.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectMethod {
	private final Class<?> clazz;
	
	private final String name;
	
	private final Class<?>[] parameterTypes;
	
	private Method method;
	
	public ReflectMethod(final Class<?> clazz, final String name, final Class<?>... parameterTypes) {
		this.clazz = clazz;
		this.name = name;
		this.parameterTypes = parameterTypes;
	}
	
	public <T> T invoke(final Object instance, final Object... args) {
		this.init();
		Object object = null;
		try {
			object = this.method.invoke(instance, args);
		} catch (final IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return object == null ? null : (T) object;
	}
	
	private void init() {
		if (this.method != null)
			return;
		try {
			try {
				this.method = this.clazz.getDeclaredMethod(this.name, this.parameterTypes);
			} catch (final NoSuchMethodException e) {
				this.method = this.clazz.getMethod(this.name, this.parameterTypes);
			}
			this.method.setAccessible(true);
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	public <T> T invokeStatic(final Object... args) {
		this.init();
		Object object = null;
		try {
			object = this.method.invoke(null, args);
		} catch (final IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return object == null ? null : (T) object;
	}
}

