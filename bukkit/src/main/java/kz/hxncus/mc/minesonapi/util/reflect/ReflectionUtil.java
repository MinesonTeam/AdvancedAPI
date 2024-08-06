package kz.hxncus.mc.minesonapi.util.reflect;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public final class ReflectionUtil {
	private final Map<Class<?>, Map<String, Field>> FIELD_CACHE = new ConcurrentHashMap<>();
	
	private String version;
	
	public <T> boolean setFieldValue(@NonNull final Object object, @NonNull final String fieldName, final T value) {
		try {
			getCachedField(object.getClass(), fieldName).set(object, value);
			return true;
		} catch (final IllegalAccessException ignored) {
			return false;
		}
	}
	
	@NonNull
	private Field getCachedField(@NonNull final Class<?> clazz, @NonNull final String fieldName) {
		Map<String, Field> classFields = FIELD_CACHE.get(clazz);
		if (classFields == null) {
			classFields = new ConcurrentHashMap<>();
			final Map<String, Field> existingFields = FIELD_CACHE.putIfAbsent(clazz, classFields);
			if (existingFields != null)
				classFields = existingFields;
		}
		return classFields.computeIfAbsent(fieldName, key -> {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (final NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	public Constructor<?> getConstructor(@NonNull final Class<?> clazz, @NonNull final Class<?> @NonNull ... parameterTypes) {
		try {
			return clazz.getConstructor(parameterTypes);
		} catch (final NoSuchMethodException e) {
			return null;
		}
	}
	
	public Constructor<?> getDeclaredConstructor(@NonNull final Class<?> clazz) {
		try {
			return clazz.getDeclaredConstructor();
		} catch (final NoSuchMethodException e) {
			return null;
		}
	}
	
	public Constructor<?> getDeclaredConstructor(@NonNull final Class<?> clazz, @NonNull final Class<?> @NonNull ... parameterTypes) {
		try {
			return clazz.getDeclaredConstructor(parameterTypes);
		} catch (final NoSuchMethodException e) {
			return null;
		}
	}
	
	public Object getFieldValue(@NonNull final Class<?> clazz, @NonNull final String name, @NonNull final Object object) {
		final Field field = getDeclaredField(clazz, name);
		if (field == null)
			throw new IllegalArgumentException("Class must have a field named: " + name);
		return getFieldValue(field, object);
	}
	
	public Field getDeclaredField(@NonNull final Class<?> clazz, @NonNull final String name) {
		try {
			return clazz.getDeclaredField(name);
		} catch (final NoSuchFieldException e) {
			return null;
		}
	}
	
	public Object getFieldValue(@NonNull final Field field, @NonNull final Object object) {
		try {
			final Object obj = field.get(object);
			if (obj instanceof Enum<?>) {
				return ((Enum<?>) obj).name();
			} else if (obj instanceof Number && ((Number) obj).doubleValue() == 0) {
				return null;
			}
			return obj;
		} catch (final IllegalAccessException e) {
			return null;
		}
	}
	
	@NonNull
	public Object newInstance(@NonNull final Constructor<?> constructor, final Object... initargs) {
		try {
			final Class<?>[] parameterTypes = constructor.getParameterTypes();
			for (int i = 0; i < initargs.length; i++) {
				if (parameterTypes[i].isEnum()) {
					initargs[i] = Enum.valueOf((Class<Enum>) parameterTypes[i], initargs[i].toString());
				}
			}
			return constructor.newInstance(initargs);
		} catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Class<?> getClass(@NonNull final String classPath) {
		try {
			return Class.forName(classPath);
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}
	
	public Class<?> getNMClass(@NonNull final String classPath) {
		try {
			return Class.forName("net.minecraft." + classPath);
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}
	
	public Class<?> getNMSClass(@NonNull final String classPath) {
		try {
			return Class.forName("net.minecraft.server." + getVersion() + "." + classPath);
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}
	
	public String getVersion() {
		if (version == null) {
			version = Bukkit.getServer()
			                .getClass()
			                .getPackage()
			                .getName()
			                .split("\\.")[3];
		}
		return version;
	}
	
	public Class<?> getObcClass(@NonNull final String classPath) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + classPath);
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}
}


