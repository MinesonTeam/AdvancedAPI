package kz.hxncus.mc.advancedapi.utility.reflect;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class Reflection util.
 *
 * @author Hxncus
 * @since 1.0.1
 */
@UtilityClass
public class ReflectionUtil {
	public static Class<?> getClass(@NonNull String path, @NonNull String name) {
		return getClass(path + "." + name);
	}
	
	public static Class<?> getInnerClass(@NonNull String path, @NonNull String name) {
		return getClass(path + "$" + name);
	}
	
	private static Class<?> getClass(@NonNull String path) {
		try {
			return Class.forName(path);
		}
		catch (ClassNotFoundException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public static Constructor<?> getConstructor(@NonNull Class<?> source, Class<?>... types) {
		try {
			Constructor<?> constructor = source.getDeclaredConstructor(types);
			constructor.setAccessible(true);
			return constructor;
		}
		catch (ReflectiveOperationException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Object invokeConstructor(@NonNull Constructor<?> constructor, Object... obj) {
		try {
			return constructor.newInstance(obj);
		}
		catch (ReflectiveOperationException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	@NonNull
	public static <T> List<T> getFields(@NonNull Class<?> source, @NonNull Class<T> type) {
		List<T> list = new ArrayList<>();
		
		for (Field field : ReflectionUtil.getFields(source)) {
			if (!field.getDeclaringClass().equals(source)) continue;
			//if (!field.canAccess(null)) continue;
			if (!Modifier.isStatic(field.getModifiers())) continue;
			if (!Modifier.isFinal(field.getModifiers())) continue;
			if (!type.isAssignableFrom(field.getType())) continue;
			if (!field.trySetAccessible()) continue;
			
			try {
				list.add(type.cast(field.get(null)));
			}
			catch (IllegalArgumentException | IllegalAccessException exception) {
				exception.printStackTrace();
			}
		}
		
		return list;
	}
	
	@NonNull
	public static List<Field> getFields(@NonNull Class<?> source) {
		List<Field> result = new ArrayList<>();
		
		Class<?> clazz = source;
		while (clazz != null && clazz != Object.class) {
			if (!result.isEmpty()) {
				result.addAll(0, Arrays.asList(clazz.getDeclaredFields()));
			}
			else {
				Collections.addAll(result, clazz.getDeclaredFields());
			}
			clazz = clazz.getSuperclass();
		}
		
		return result;
	}
	
	public static Field getField(@NonNull Class<?> source, @NonNull String name) {
		try {
			return source.getDeclaredField(name);
		}
		catch (NoSuchFieldException exception) {
			Class<?> superClass = source.getSuperclass();
			return superClass == null ? null : getField(superClass, name);
		}
	}
	
	public static Object getFieldValue(@NonNull Object source, @NonNull String name) {
		try {
			Class<?> clazz = source instanceof Class<?> ? (Class<?>) source : source.getClass();
			Field field = getField(clazz, name);
			if (field == null) return null;
			
			field.setAccessible(true);
			return field.get(source);
		}
		catch (IllegalAccessException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public static boolean setFieldValue(@NonNull Object source, @NonNull String name, @Nullable Object value) {
		try {
			boolean isStatic = source instanceof Class;
			Class<?> clazz = isStatic ? (Class<?>) source : source.getClass();
			
			Field field = getField(clazz, name);
			if (field == null) return false;
			
			field.setAccessible(true);
			field.set(isStatic ? null : source, value);
			return true;
		}
		catch (IllegalAccessException exception) {
			exception.printStackTrace();
		}
		return false;
	}
	
	public static Method getMethod(@NonNull Class<?> source, @NonNull String name, @NonNull Class<?>... params) {
		try {
			return source.getDeclaredMethod(name, params);
		}
		catch (NoSuchMethodException exception) {
			Class<?> superClass = source.getSuperclass();
			return superClass == null ? null : getMethod(superClass, name);
		}
	}
	
	public static Object invokeMethod(@NonNull Method method, @Nullable Object by, @Nullable Object... param) {
		method.setAccessible(true);
		try {
			return method.invoke(by, param);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
			exception.printStackTrace();
		}
		return null;
	}
}


