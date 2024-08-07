package kz.hxncus.mc.minesonapi.util.reflect;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class Reflection util.
 *
 * @author Hxncus
 * @since 1.0.1
 */
@UtilityClass
public class ReflectionUtil {
	private final Map<Class<?>, Map<String, Field>> FIELD_CACHE = new ConcurrentHashMap<>(8);
	
	private String version;
	
	/**
	 * Sets field value.
	 *
	 * @param <T>       the type parameter
	 * @param object    the object
	 * @param fieldName the field name
	 * @param value     the value
	 * @return the field value
	 */
	@SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
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
			classFields = new ConcurrentHashMap<>(8);
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
	
	/**
	 * Gets constructor.
	 *
	 * @param clazz          the clazz
	 * @param parameterTypes the parameter types
	 * @return the constructor
	 */
	public Constructor<?> getConstructor(@NonNull final Class<?> clazz, @NonNull final Class<?> @NonNull ... parameterTypes) {
		try {
			return clazz.getConstructor(parameterTypes);
		} catch (final NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * Gets declared constructor.
	 *
	 * @param clazz the clazz
	 * @return the declared constructor
	 */
	public Constructor<?> getDeclaredConstructor(@NonNull final Class<?> clazz) {
		try {
			return clazz.getDeclaredConstructor();
		} catch (final NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * Gets declared constructor.
	 *
	 * @param clazz          the clazz
	 * @param parameterTypes the parameter types
	 * @return the declared constructor
	 */
	public Constructor<?> getDeclaredConstructor(@NonNull final Class<?> clazz, @NonNull final Class<?> @NonNull ... parameterTypes) {
		try {
			return clazz.getDeclaredConstructor(parameterTypes);
		} catch (final NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * Gets field value.
	 *
	 * @param clazz  the clazz
	 * @param name   the name
	 * @param object the object
	 * @return the field value
	 */
	public Object getFieldValue(@NonNull final Class<?> clazz, @NonNull final String name, @NonNull final Object object) {
		final Field field = getDeclaredField(clazz, name);
		if (field == null)
			throw new IllegalArgumentException("Class must have a field named: " + name);
		return getFieldValue(field, object);
	}
	
	/**
	 * Gets declared field.
	 *
	 * @param clazz the clazz
	 * @param name  the name
	 * @return the declared field
	 */
	public Field getDeclaredField(@NonNull final Class<?> clazz, @NonNull final String name) {
		try {
			return clazz.getDeclaredField(name);
		} catch (final NoSuchFieldException e) {
			return null;
		}
	}
	
	/**
	 * Gets field value.
	 *
	 * @param field  the field
	 * @param object the object
	 * @return the field value
	 */
	public Object getFieldValue(@NonNull final Field field, @NonNull final Object object) {
		try {
			final Object obj = field.get(object);
			if (obj instanceof Enum<?>) {
				return ((Enum<?>) obj).name();
			} else if (obj instanceof Number && ((Number) obj).doubleValue() == 0) {
				return null;
			}
			return obj;
		} catch (final IllegalAccessException ignored) {
		}
		return null;
	}
	
	/**
	 * New instance object.
	 *
	 * @param constructor the constructor
	 * @param initargs    the initargs
	 * @return the object
	 */
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
	
	/**
	 * Gets class.
	 *
	 * @param classPath the class path
	 * @return the class
	 */
	public Class<?> getClass(@NonNull final String classPath) {
		try {
			return Class.forName(classPath);
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Gets nm class.
	 *
	 * @param classPath the class path
	 * @return the nm class
	 */
	public Class<?> getNMClass(@NonNull final String classPath) {
		try {
			return Class.forName("net.minecraft." + classPath);
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Gets nms class.
	 *
	 * @param classPath the class path
	 * @return the nms class
	 */
	public Class<?> getNMSClass(@NonNull final String classPath) {
		try {
			return Class.forName("net.minecraft.server." + getVersion() + "." + classPath);
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Gets version.
	 *
	 * @return the version
	 */
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
	
	/**
	 * Gets obc class.
	 *
	 * @param classPath the class path
	 * @return the obc class
	 */
	public Class<?> getObcClass(@NonNull final String classPath) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + classPath);
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}
}


