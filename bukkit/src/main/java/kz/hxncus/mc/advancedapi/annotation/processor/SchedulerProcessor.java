package kz.hxncus.mc.advancedapi.annotation.processor;

import kz.hxncus.mc.advancedapi.annotation.Async;
import kz.hxncus.mc.advancedapi.annotation.Schedule;
import kz.hxncus.mc.advancedapi.utility.ReflectionUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

@UtilityClass
public class SchedulerProcessor {
    public void process(JavaPlugin plugin, Class<?> clazz, Object obj) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Schedule.class)) {
                continue;
            }
            Schedule schedule = method.getAnnotation(Schedule.class);
            boolean async = clazz.isAnnotationPresent(Async.class);
            if (async) {
                Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> ReflectionUtil.invoke(method, obj), schedule.delay(), schedule.period());
            } else {
                Bukkit.getScheduler().runTaskTimer(plugin, () -> ReflectionUtil.invoke(method, obj), schedule.delay(), schedule.period());
            }
        }
    }
}

