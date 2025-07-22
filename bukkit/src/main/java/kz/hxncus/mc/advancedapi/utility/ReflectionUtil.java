package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@UtilityClass
public class ReflectionUtil {
    public boolean isInstance(Class<?> clazz, Object obj) {
        return clazz.isInstance(obj);
    }

    public boolean isInstance(Object clazz, Object obj) {
        return ReflectionUtil.isInstance(clazz.getClass(), obj);
    }

    public <T> T cast(Class<? extends T> clazz, Object obj) {
        if (ReflectionUtil.isInstance(clazz, obj)) {
            return clazz.cast(obj);
        }
        return null;
    }

    public <T> T cast(Object clazz, Object obj) {
        if (ReflectionUtil.isInstance(clazz, obj)) {
            return (T) obj;
        }
        return null;
    }

    public void setAccessible(AccessibleObject obj, boolean flag) {
        if (obj.isAccessible() != flag) {
            obj.setAccessible(flag);
        }
    }

    public void invoke(Method method, Object obj, Object... args) {
        ReflectionUtil.setAccessible(method, true);
        try {
            method.invoke(obj, args);
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        } catch (Exception ignored) {
            // Ignored
        }
    }

    public void setField(Field field, Object obj, Object value) {
        ReflectionUtil.setAccessible(field, true);
        try {
            if (Modifier.isStatic(field.getModifiers())) {
                field.set(null, value);
            } else {
                field.set(obj, value);
            }
        } catch (IllegalArgumentException e) {
            e.getCause().printStackTrace();
        } catch (Exception ignored) {
            // Ignored
        }
    }

    public void setPrimitiveField(Field field, Object obj, Object value) {
        ReflectionUtil.setAccessible(field, true);
        try {
            if (value != null) {
                if (field.getType() == int.class) {
                    field.setInt(obj, Integer.parseInt(value.toString()));
                } else if (field.getType() == boolean.class) {
                    field.setBoolean(obj, Boolean.getBoolean(value.toString()));
                } else if (field.getType() == byte.class) {
                    field.setByte(obj, Byte.parseByte(value.toString()));
                } else if (field.getType() == char.class) {
                    field.setChar(obj, value.toString().charAt(0));
                } else if (field.getType() == double.class) {
                    field.setDouble(obj, Double.parseDouble(value.toString()));
                } else if (field.getType() == float.class) {
                    field.setFloat(obj, Float.parseFloat(value.toString()));
                } else if (field.getType() == long.class) {
                    field.setLong(obj, Long.parseLong(value.toString()));
                } else if (field.getType() == short.class) {
                    field.setShort(obj, Short.parseShort(value.toString()));
                } else {
                    setField(field, obj, value);
                }
            } else {
                setField(field, obj, null);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Field getField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (Exception ignored) {
            return null;
        }
    }
    
    public Field getDeclaredField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (Exception ignored) {
            return null;
        }
    }
    
    public Field getField(Object object, String name) {
        Field field = ReflectionUtil.getField(object.getClass(), name);
        if (field != null) {
            return field;
        }
        return ReflectionUtil.getField(object.getClass().getSuperclass(), name);
    }
    
    public Field getDeclaredField(Object object, String name) {
        Field field = ReflectionUtil.getDeclaredField(object.getClass(), name);
        if (field != null) {
            return field;
        }
        return ReflectionUtil.getDeclaredField(object.getClass().getSuperclass(), name);
    }

    public <T> T getFieldValue(Field field, Object obj) {
        ReflectionUtil.setAccessible(field, true);
        try {
            Object value = field.get(obj);
            if (value == null || !ReflectionUtil.isInstance(value, value)) {
                return null;
            }
            return (T) value;
        } catch (IllegalArgumentException e) {
            e.getCause().printStackTrace();
        } catch (Exception ignored) {
            // Ignored
        }
        return null;
    }

    public <T> T getFieldValue(String fieldName, Object obj) {
        Field field = ReflectionUtil.getField(obj, fieldName);
        if (field == null) {
            return null;
        }
        return getFieldValue(field, obj);
    }

    public <T> T getDeclaredFieldValue(Object obj, String name) {
        Field field = ReflectionUtil.getDeclaredField(obj, name);
        if (field == null) {
            return null;
        }
        return getFieldValue(field, obj);
    }

    public Object newInstance(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterCount() != 0) {
                continue;
            }
            ReflectionUtil.setAccessible(constructor, true);
            try {
                return constructor.newInstance();
            } catch (InvocationTargetException e) {
                if (!(e.getCause() instanceof UnsupportedOperationException)) {
                    e.getCause().printStackTrace();
                }
            } catch (Exception ignored) {
                // Ignored
            }
        }
        return null;
    }

    public List<Class<?>> findAnnotatedClasses(JavaPlugin plugin, Class<? extends Annotation> annotation) {
        String pluginClassName = plugin.getClass().getName();
        String path = pluginClassName.substring(0, pluginClassName.lastIndexOf('.'));
        List<Class<?>> result = new ArrayList<>();
        try {
            URL codeSourceLocation = plugin.getClass().getProtectionDomain().getCodeSource().getLocation();
            String jarPath = codeSourceLocation.getPath();

            // Исправление для Windows-путей
            if (jarPath.startsWith("/") && System.getProperty("os.name").contains("Windows")) {
                jarPath = jarPath.substring(1);
            }

            try (JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, "UTF-8"))) {
                Enumeration<JarEntry> entries = jarFile.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (!name.endsWith(".class")) {
                        continue;
                    }
                    String className = name.replace('/', '.').replace(".class", "");
                    if (!className.startsWith(path)) {
                        continue;
                    }
                    try {
                        List<AnnotatedElement> elements = new ArrayList<>();
                        Class<?> clazz = Class.forName(className, false, plugin.getClass().getClassLoader());
                        elements.add(clazz);
                        elements.addAll(Arrays.asList(clazz.getDeclaredFields()));
                        elements.addAll(Arrays.asList(clazz.getDeclaredMethods()));
                        for (AnnotatedElement element : elements) {
                            if (element.isAnnotationPresent(annotation)) {
                                result.add(clazz);
                                break; // Не проверяем остальные элементы
                            }
                        }
                    } catch (Exception e) {
                        plugin.getLogger().warning("Failed to load class: " + className + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Error scanning plugin JAR: " + e.getMessage());
        }
        return result;
    }
}
