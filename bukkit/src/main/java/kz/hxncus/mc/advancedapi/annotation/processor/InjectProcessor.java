package kz.hxncus.mc.advancedapi.annotation.processor;

import kz.hxncus.mc.advancedapi.annotation.Inject;
import kz.hxncus.mc.advancedapi.annotation.Provide;
import kz.hxncus.mc.advancedapi.utility.ReflectionUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

@UtilityClass
public class InjectProcessor {
    public void processStatic(Plugin plugin, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Inject.class)) {
                continue;
            }
            Class<?> fieldType = field.getType();
            if (Plugin.class.isAssignableFrom(fieldType)) {
                ReflectionUtil.setField(field, null, plugin);
            } else if (field.getType() == Logger.class) {
                ReflectionUtil.setField(field, null, LoggerFactory.getLogger(clazz.getSimpleName()));
            }
        }
    }
    public void process(Plugin plugin, Class<?> clazz, Map<Class<?>, Object> instances) {
        Object instance = plugin;
        if (instance == null || !Plugin.class.isAssignableFrom(clazz)) {
            instance = newInstance(clazz, instances);
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Inject.class)) {
                continue;
            }
            Class<?> fieldType = field.getType();
            if (Plugin.class.isAssignableFrom(fieldType)) {
                ReflectionUtil.setField(field, instance, plugin);
            } else if (field.getType() == Logger.class) {
                ReflectionUtil.setField(field, instance, LoggerFactory.getLogger(clazz.getSimpleName()));
            } else {
                if (!instances.containsKey(fieldType)) {
                    plugin.getLogger().severe("Cannot inject field " + field.getName() + " of class " + clazz.getName() + " because there is no instance of " + fieldType.getName() + " provided.");
                    return;
                }
                ReflectionUtil.setField(field, instance, instances.get(fieldType));
            }
        }
    }

    public Object newInstance(Class<?> clazz, Map<Class<?>, Object> instances) {
        Object instance = instances.get(clazz);
        if (instance != null) {
            return instance;
        }
        instance = ReflectionUtil.newInstance(clazz);
        if (instance != null) {
            instances.put(clazz, instance);
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.getType().isAnnotationPresent(Provide.class)) {
                    continue;
                }
                Object fieldInstance = newInstance(field.getType(), instances);
                ReflectionUtil.setField(field, instance, fieldInstance);
            }
        }
        return instance;
    }
}
