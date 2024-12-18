package kz.hxncus.mc.advancedapi.utility.reflection;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Predicate;

public class ReflectionObject {
    private static final byte FLAG_ACCESS = 1;
    private static final byte FLAG_FINAL = 1 << 1;

    private static final VarHandle modifiers;

    private final Object object;
    private final Class<?> clazz;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup());
            modifiers = lookup.findVarHandle(Field.class, "modifiers", int.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Получаем из готового объекта
     */
    public ReflectionObject(Object object) {
        this.object = object;
        this.clazz = object.getClass();
    }

    /**
     * Получаем из объекта и класса
     */
    public ReflectionObject(Object object, Class<?> clazz) {
        this.object = object;
        this.clazz = clazz;
    }

    /**
     * Получаем из класса
     */
    public ReflectionObject(Class<?> clazz) {
        this.object = null;
        this.clazz = clazz;
    }

    /**
     * Получаем из объекта
     */
    public static ReflectionObject wrap(Object obj) {
        return new ReflectionObject(obj);
    }

    /**
     * Получаем из класса
     */
    public static ReflectionObject wrap(Class<?> clazz) {
        return new ReflectionObject(clazz);
    }

    /**
     * Получаем из объекта и класса
     */
    public static ReflectionObject wrap(Object obj, Class<?> clazz) {
        return new ReflectionObject(obj, clazz);
    }

    /**
     * Получаем поле по названию
     */
    private Field findField(String name) throws NoSuchFieldException {
        Field field = null;
        Class<?> c = this.clazz;
        do {
            try {
                field = c.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {}
            if (field != null) {
                break;
            }
        }
        while ((c = c.getSuperclass()) != null);

        if (field == null) {
            throw new NoSuchFieldException(name);
        }
        return field;
    }

    /**
     * Получай переменные.
     * Не нужно беспокоиться о приватности пеменной!
     * Использование:
     * <p>
     * object.getField("temp");
     * где:
     * * "temp" - название переменной
     *
     * @param name название переменной
     * @return MyObject этой переменной (ее можно получить через .getObject())
     */
    public ReflectionObject getField(String name) {
        return unchecked(() -> {
            final Field field = this.findField(name);
            final byte flags = unaccessField(field, false);

            Object get;
            try {
                get = field.get(this.object);
            } catch (Exception e) {
                get = null;
            } finally {
                accessField(field, flags);
            }
            
            return new ReflectionObject(get, get != null ? get.getClass() : field.getType());
        });
    }

    public static void accessField(Field field, byte flags) {
        ReflectionObject.accessObject(field, flags);

        if ((flags & FLAG_FINAL) != 0 && (field.getModifiers() & Modifier.FINAL) == 0) {
            modifiers.set(field, field.getModifiers() | Modifier.FINAL);
        }
    }

    public static byte unaccessField(Field field, boolean unfinal) {
        byte flags = ReflectionObject.unaccessObject(field);

        if (unfinal && (field.getModifiers() & Modifier.FINAL) != 0) {
            modifiers.set(field, field.getModifiers() & ~Modifier.FINAL);
            flags |= FLAG_FINAL;
        }

        return flags;
    }
    
    private static void accessObject(AccessibleObject object, byte flags) {
        if ((flags & FLAG_ACCESS) != 0 && object.canAccess(null)) {
            object.setAccessible(false);
        }
    }

    private static byte unaccessObject(AccessibleObject object) {
        byte flags = 0;
        if (!object.canAccess(null)) {
            object.setAccessible(true);
            flags |= FLAG_ACCESS;
        }
        return flags;
    }

    /**
     * Установить значение переменной
     *
     * @param name  имя переменной
     * @param value значение
     */
    public void setField(String name, Object value) {
        unchecked(() -> {
            final Field field = this.findField(name);

            final byte flags = unaccessField(field, true);

            try {
                field.set(this.object, value instanceof ReflectionObject ? ((ReflectionObject) value).getObject() : value);
            } finally {
                accessField(field, flags);
            }
        });
    }

    /**
     * Вызывай методы.
     * Не нужно беспокоится о приватности метода!
     * Не нужно беспокоится о аргументах метода,
     * моя система сама все кастит и т.п.
     * Если этот метод функция, то вернет значение, иначе null.
     * Использование:
     * <p>
     * object.invokeMethod("exetute", true, Integer.forNumber(10));
     * где:
     * * "exetute" - название метода
     * * true - (первый аргумент метода, boolean значение)
     * * Integer.forNumber(10) - (второй аргумент метода) пример того, что неважно, что указывать (int или Integer)
     *
     * @param name название метода
     * @param args аргументы (Можно указывать сразу MyObject, оно само достанет)
     * @return если этот метод функция, то вернет что-то, иначе null
     */
    public ReflectionObject invokeMethod(String name, Object... args) {
        return unchecked(() -> {
            this.fixArgs(args);
            Method method = this.findMethod(name, args);

            final byte flags = unaccessObject(method);

            Object returnObject;
            try {
                returnObject = method.invoke(this.object, args);
            } finally {
                accessObject(method, flags);
            }

            return new ReflectionObject(returnObject, returnObject != null ? returnObject.getClass() : method.getReturnType());
        });
    }

    @SuppressWarnings("ConstantConditions")
    public ReflectionObject newInstance(Object... args) {
        return unchecked(() -> {
            this.fixArgs(args);
            Constructor<?> target = findConstructor(args);
            final byte flags = unaccessObject(target);

            Object instance;
            try{
                instance = target.newInstance(args);
            } finally {
                accessObject(target, flags);
            }

            return new ReflectionObject(instance, instance != null ? instance.getClass() : target.getDeclaringClass());
        });
    }

    private Method findMethod(String name, Object[] args) throws NoSuchMethodException {
        Class<?> c = this.clazz;
        Method method = null;
        one:
        do {
            two:
            for (Method m : c.getDeclaredMethods()) {
                if (!m.getName().equals(name)) {
                    continue;
                }
                if (m.getParameterCount() != args.length) {
                    continue;
                }

                for (int i = 0; i < m.getParameterCount(); i++) {
                    if (args[i] != null) {
                        if (!m.getParameterTypes()[i].isInstance(args[i])) {
                            continue two;
                        }
                    }
                }
                method = m;
                break one;
            }
        }
        while ((c = c.getSuperclass()) != null);

        if (method == null) {
            throw new NoSuchMethodException(name + " (" + Arrays.toString(args) + ")");
        }

        return method;
    }

    private Constructor<?> findConstructor(Object[] args) throws NoSuchMethodException {
        Class<?> current = this.clazz;
        Constructor<?> constructor = null;

        do {
            constructorSkip:
            for(Constructor<?> declaredConstructor : current.getDeclaredConstructors()){
                if(declaredConstructor.getParameterTypes().length != args.length) continue;

                for(int i = 0; i < declaredConstructor.getParameterCount(); i++){
                    if(!declaredConstructor.getParameterTypes()[i].isInstance(args[i])) continue constructorSkip;
                }

                constructor = declaredConstructor;
            }
        } while((current = current.getSuperclass()) != null);

        if(constructor == null) {
            throw new NoSuchMethodException(String.format("%s.<init> (%s)", this.clazz.getName(), Arrays.toString(args)));
        }

        return constructor;
    }

    /**
     * Получить объект, с которым работаем
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> cast) {
        return (T) object;
    }

    /**
     * Получить объект, с которым работаем
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject() {
        return (T) object;
    }

    private void fixArgs(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ReflectionObject) {
                args[i] = ((ReflectionObject) args[i]).getObject();
            }
        }
    }


    /**
     * Игрорировать проверяемое исключение (если проверяемое исключение возникнет, оно будет в обертке RuntimeException)
     *
     * @param supplier code
     * @param <T>      type result
     * @return result
     */
    public static <T> T unchecked(SupplierThrows<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            doThrow0(e);
            throw new AssertionError(); // до сюда код не дойдет
        }
    }

    /**
     * Игрорировать проверяемое исключение (если проверяемое исключение возникнет, оно будет в обертке RuntimeException)
     *
     * @param runnable code
     */
    public static void unchecked(RunnableThrows runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            doThrow0(e);
            throw new AssertionError(); // до сюда код не дойдет
        }
    }

    /**
     * Игрорировать проверяемое исключение (если проверяемое исключение возникнет, оно будет в обертке RuntimeException)
     *
     * @param predicate code
     */
    public static <T> Predicate<T> unchecked(PredicateThrows<T> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                doThrow0(e);
                throw new AssertionError(); // до сюда код не дойдет
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Exception> void doThrow0(Exception e) throws E {
        throw (E) e;
    }

    public interface SupplierThrows<T> {

        T get() throws Exception;
    }

    public interface RunnableThrows {

        void run() throws Exception;
    }

    public interface PredicateThrows<T> {

        boolean test(T val) throws Exception;
    }
}
