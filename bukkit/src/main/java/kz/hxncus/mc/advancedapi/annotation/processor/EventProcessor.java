package kz.hxncus.mc.advancedapi.annotation.processor;

import kz.hxncus.mc.advancedapi.annotation.Async;
import kz.hxncus.mc.advancedapi.annotation.RequireEntity;
import kz.hxncus.mc.advancedapi.annotation.RequirePermission;
import kz.hxncus.mc.advancedapi.annotation.RequireWorld;
import kz.hxncus.mc.advancedapi.bukkit.event.EventDispatcher;
import kz.hxncus.mc.advancedapi.utility.ArrayUtil;
import kz.hxncus.mc.advancedapi.utility.ReflectionUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.world.WorldEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@UtilityClass
public class EventProcessor {
    public void process(JavaPlugin plugin, Class<?> clazz, Object obj) {
        EventDispatcher dispatcher = new EventDispatcher(plugin);
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class)) {
                continue;
            }
            EventHandler event = method.getAnnotation(EventHandler.class);
            boolean async = method.isAnnotationPresent(Async.class);
            Parameter[] params = method.getParameters();
            if (params.length != 1 && !Event.class.isAssignableFrom(params[0].getType())) {
                plugin.getLogger().warning("Invalid event handler signature: " + method.getName());
                continue;
            }
            RequireEntity requireEntity = method.getAnnotation(RequireEntity.class);
            RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);
            RequireWorld requireWorld = method.getAnnotation(RequireWorld.class);
            dispatcher.register((Class<? extends Event>) params[0].getType(), event.priority(), e -> {
                if (e instanceof PlayerEvent) {
                    Player player = ((PlayerEvent) e).getPlayer();
                    String worldName = player.getWorld().getName();
                    if (requirePermission != null && !player.hasPermission(requirePermission.value())) {
                        return;
                    }
                    if (requireWorld != null && !worldName.equals(requireWorld.value())) {
                        return;
                    }
                } else if (e instanceof EntityEvent) {
                    Entity entity = ((EntityEvent) e).getEntity();
                    if (requireEntity != null && !ArrayUtil.contains(requireEntity.value(), entity.getType())) {
                        return;
                    }
                    if (requirePermission != null && !entity.hasPermission(requirePermission.value())) {
                        return;
                    }
                    String worldName = entity.getWorld().getName();
                    if (requireWorld != null && !worldName.equals(requireWorld.value())) {
                        return;
                    }
                } else if (e instanceof WorldEvent) {
                    World world = ((WorldEvent) e).getWorld();
                    String worldName = world.getName();
                    if (requireWorld != null && !worldName.equals(requireWorld.value())) {
                        return;
                    }
                }
                if (async) {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> ReflectionUtil.invoke(method, obj, e));
                } else {
                    ReflectionUtil.invoke(method, obj, e);
                }
            }, event.ignoreCancelled());
        }
    }
}
