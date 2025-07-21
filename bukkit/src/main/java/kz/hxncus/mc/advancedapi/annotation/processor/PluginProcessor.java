package kz.hxncus.mc.advancedapi.annotation.processor;

import kz.hxncus.mc.advancedapi.annotation.OnPluginDisable;
import kz.hxncus.mc.advancedapi.annotation.OnPluginEnable;
import kz.hxncus.mc.advancedapi.utility.ReflectionUtil;
import kz.hxncus.mc.advancedapi.utility.tuples.Pair;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class PluginProcessor {
    private final Collection<Pair<Pair<Method, Object>, Integer>> enableMethods = new CopyOnWriteArrayList<>();
    private final Collection<Pair<Pair<Method, Object>, Integer>> disableMethods = new CopyOnWriteArrayList<>();
    public void process(Class<?> clazz, Object obj) {
        for (Method method : clazz.getDeclaredMethods()) {
            OnPluginEnable onPluginEnable = method.getAnnotation(OnPluginEnable.class);
            if (onPluginEnable != null) {
                enableMethods.add(new Pair<>(new Pair<>(method, obj), onPluginEnable.priority()));
            }
            OnPluginDisable onPluginDisable = method.getAnnotation(OnPluginDisable.class);
            if (onPluginDisable != null) {
                disableMethods.add(new Pair<>(new Pair<>(method, obj), onPluginDisable.priority()));
            }
        }
    }

    public void enable(JavaPlugin plugin) {
        List<Pair<Pair<Method, Object>, Integer>> sorted = enableMethods.stream()
                .sorted(Comparator.comparingInt(Pair<Pair<Method, Object>, Integer>::getSecond).reversed())
                .collect(Collectors.toList());

        for (Pair<Pair<Method, Object>, Integer> pair : sorted) {
            Pair<Method, Object> methodPair = pair.getFirst();
            ReflectionUtil.invoke(methodPair.getFirst(), methodPair.getSecond(), plugin);
        }
    }

    public void disable(JavaPlugin plugin) {
        List<Pair<Pair<Method, Object>, Integer>> sorted = disableMethods.stream()
                .sorted(Comparator.comparingInt(Pair<Pair<Method, Object>, Integer>::getSecond).reversed())
                .collect(Collectors.toList());

        for (Pair<Pair<Method, Object>, Integer> pair : sorted) {
            Pair<Method, Object> methodPair = pair.getFirst();
            ReflectionUtil.invoke(methodPair.getFirst(), methodPair.getSecond(), plugin);
        }
    }
}
