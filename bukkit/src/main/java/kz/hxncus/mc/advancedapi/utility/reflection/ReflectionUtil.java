package kz.hxncus.mc.advancedapi.utility.reflection;

import java.lang.reflect.Field;

import com.google.common.base.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReflectionUtil {
    public boolean isInstance(Class<?> clazz, Object obj) {
        return clazz.isInstance(obj);
    }

    public boolean isInstance(Object clazz, Object obj) {
        return ReflectionUtil.isInstance(clazz.getClass(), obj);
    }

    public <T> T cast(Class<? extends T> clazz, T obj) {
        if (ReflectionUtil.isInstance(clazz, obj)) {
            return clazz.cast(obj);
        }
        return null;
    }

    public <T> T cast(Object clazz, T obj) {
        if (ReflectionUtil.isInstance(clazz, obj)) {
            return (T) obj;
        }
        return null;
    }

    public Optional<Field> getField(Class<?> clazz, String name) {
        try {
            return Optional.of(clazz.getDeclaredField(name));
        } catch (NoSuchFieldException e) {
            return Optional.absent();
        }
    }
    
    public Optional<Field> getDeclaredField(Class<?> clazz, String name) {
        try {
            return Optional.of(clazz.getDeclaredField(name));
        } catch (NoSuchFieldException e) {
            return Optional.absent();
        }
    }
    
    public Optional<Field> getField(Object object, String name) {
        Optional<Field> field = ReflectionUtil.getField(object.getClass(), name);
        return field.or(ReflectionUtil.getField(object.getClass().getSuperclass(), name));
    }
    
    public Optional<Field> getDeclaredField(Object object, String name) {
        Optional<Field> field = ReflectionUtil.getDeclaredField(object.getClass(), name);
        return field.or(ReflectionUtil.getDeclaredField(object.getClass().getSuperclass(), name));
    }
    
    public <T> T getFieldValue(Object obj, String name) {
        Optional<Field> fieldOptional = ReflectionUtil.getField(obj, name);
        if (!fieldOptional.isPresent()) {
            return null;
        }
        Field field = fieldOptional.get();
        field.setAccessible(true);
        try {
            Object result = field.get(obj);
            if (result == null || !ReflectionUtil.isInstance(result, result)) {
                return null;
            }
            return (T) result;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Failed to get field value", e);
        }
    }

    public <T> T getDeclaredFieldValue(Object obj, String name) {
        Optional<Field> fieldOptional = ReflectionUtil.getDeclaredField(obj, name);
        if (!fieldOptional.isPresent()) {
            return null;
        }
        Field field = fieldOptional.get();
        field.setAccessible(true);
        try {
            Object result = field.get(obj);
            if (result == null || !ReflectionUtil.isInstance(result, result)) {
                return null;
            }
            return (T) result;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Failed to get field value", e);
        }
    }
}
