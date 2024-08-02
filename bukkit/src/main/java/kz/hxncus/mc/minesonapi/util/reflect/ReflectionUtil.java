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

    public <T> boolean setFieldValue(@NonNull Object object, @NonNull String fieldName, T value) {
        try {
            getCachedField(object.getClass(), fieldName).set(object, value);
            return true;
        } catch (IllegalAccessException ignored) {
            return false;
        }
    }

    @NonNull
    private Field getCachedField(@NonNull Class<?> clazz, @NonNull String fieldName) {
        Map<String, Field> classFields = FIELD_CACHE.get(clazz);
        if (classFields == null) {
            classFields = new ConcurrentHashMap<>();
            Map<String, Field> existingFields = FIELD_CACHE.putIfAbsent(clazz, classFields);
            if (existingFields != null)
                classFields = existingFields;
        }
        return classFields.computeIfAbsent(fieldName, key -> {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
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

    public Field getDeclaredField(@NonNull Class<?> clazz, @NonNull String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public Constructor<?> getConstructor(@NonNull Class<?> clazz, @NonNull Class<?> @NonNull... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public Constructor<?> getDeclaredConstructor(@NonNull Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public Constructor<?> getDeclaredConstructor(@NonNull Class<?> clazz, @NonNull Class<?> @NonNull... parameterTypes) {
        try {
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public Object getFieldValue(@NonNull Class<?> clazz, @NonNull String name, @NonNull Object object) {
        Field field = getDeclaredField(clazz, name);
        if (field == null)
            throw new IllegalArgumentException("Class must have a field named: " + name);
        return getFieldValue(field, object);
    }

    public Object getFieldValue(@NonNull Field field, @NonNull Object object) {
        try {
            Object obj = field.get(object);
            if (obj instanceof Enum<?> e) {
                return e.name();
            } else if (obj instanceof Number number && number.doubleValue() == 0) {
                return null;
            }
            return obj;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    @NonNull
    public Object newInstance(@NonNull Constructor<?> constructor, Object... initargs) {
        try {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (int i = 0; i < initargs.length; i++) {
                if (parameterTypes[i].isEnum()) {
                    initargs[i] = Enum.valueOf((Class<Enum>) parameterTypes[i], initargs[i].toString());
                }
            }
            return constructor.newInstance(initargs);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> getClass(@NonNull String classPath) {
        try {
            return Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public Class<?> getNMClass(@NonNull String classPath) {
        try {
            return Class.forName("net.minecraft." + classPath);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public Class<?> getNMSClass(@NonNull String classPath) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + classPath);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public Class<?> getObcClass(@NonNull String classPath) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + classPath);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}


