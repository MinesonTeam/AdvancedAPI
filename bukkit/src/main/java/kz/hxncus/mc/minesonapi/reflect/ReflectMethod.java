package kz.hxncus.mc.minesonapi.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectMethod {
    private final Class<?> clazz;

    private final String name;

    private final Class<?>[] parameterTypes;

    private Method method;

    public ReflectMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        this.clazz = clazz;
        this.name = name;
        this.parameterTypes = parameterTypes;
    }

    private void init() {
        if (this.method != null)
            return;
        try {
            try {
                this.method = this.clazz.getDeclaredMethod(this.name, this.parameterTypes);
            } catch (NoSuchMethodException e) {
                this.method = this.clazz.getMethod(this.name, this.parameterTypes);
            }
            this.method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public <T> T invoke(Object instance, Object... args) {
        init();
        Object object = null;
        try {
            object = this.method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return object == null ? null : (T) object;
    }

    public <T> T invokeStatic(Object... args) {
        init();
        Object object = null;
        try {
            object = this.method.invoke(null, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return object == null ? null : (T) object;
    }
}

