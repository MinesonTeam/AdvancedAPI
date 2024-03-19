package kz.hxncus.mc.minesonapi.reflect;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectionUtil {
    private ReflectionUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static final Map<Class<?>, Map<String, Field>> FIELD_CACHE = new ConcurrentHashMap<>();

    private static String version;

    public static <T> boolean setFieldValue(@NotNull Object object, @NotNull String fieldName, @Nullable T value) {
        Class<?> clazz = object.getClass();
        Field field = getCachedField(clazz, fieldName);
        if (field != null)
            try {
                field.set(object, value);
                return true;
            } catch (IllegalAccessException ignored) {}
        return false;
    }

    @Nullable
    private static Field getCachedField(@NotNull Class<?> clazz, @NotNull String fieldName) {
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

    public static String getVersion() {
        if (version == null)
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return version;
    }

    @Nullable
    public static Class<?> getClass(@NotNull String classPath) {
        try {
            return Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Class<?> getNMClass(@NotNull String classPath) {
        try {
            return Class.forName("net.minecraft." + classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Class<?> getNMSClass(@NotNull String classPath) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Class<?> getObcClass(@NotNull String classPath) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}


