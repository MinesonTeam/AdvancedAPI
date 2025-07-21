package kz.hxncus.mc.advancedapi.annotation.scanner;

import kz.hxncus.mc.advancedapi.annotation.*;
import kz.hxncus.mc.advancedapi.annotation.processor.*;
import kz.hxncus.mc.advancedapi.bukkit.event.EventDispatcher;
import kz.hxncus.mc.advancedapi.bukkit.scheduler.AdvancedScheduler;
import kz.hxncus.mc.advancedapi.utility.CommandUtil;
import kz.hxncus.mc.advancedapi.utility.ReflectionUtil;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class AnnotationScanner {
    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final JavaPlugin plugin;
    private final PluginProcessor pluginProcessor;

    public AnnotationScanner(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginProcessor = new PluginProcessor();
    }

    public void load() {
        List<Class<?>> provides = ReflectionUtil.findAnnotatedClasses(plugin, Provide.class);
        for (Class<?> provide : provides) {
            newInstance(provide);
        }
        List<Class<?>> injects = ReflectionUtil.findAnnotatedClasses(plugin, Inject.class);
        for (Class<?> inject : injects) {
            InjectProcessor.process(plugin, inject, instances);
        }
        List<Class<?>> inventories = ReflectionUtil.findAnnotatedClasses(plugin, Inventory.class);
        for (Class<?> inventory : inventories) {
            Object instance = newInstance(inventory);
            InventoryProcessor.process(inventory, instance);
        }
        List<Class<?>> configs = ReflectionUtil.findAnnotatedClasses(plugin, Config.class);
        for (Class<?> config : configs) {
            ConfigProcessor.process(plugin, config);
        }
        List<Class<?>> holograms = ReflectionUtil.findAnnotatedClasses(plugin, Hologram.class);
        for (Class<?> hologram : holograms) {
            Object instance = newInstance(hologram);
            HologramProcessor.process(plugin, hologram, instance);
        }
        List<Class<?>> commands = ReflectionUtil.findAnnotatedClasses(plugin, Command.class);
        for (Class<?> command : commands) {
            Object instance = newInstance(command);
            CommandProcessor.register(command, instance);
        }
        List<Class<?>> schedulers = ReflectionUtil.findAnnotatedClasses(plugin, Schedule.class);
        for (Class<?> scheduler : schedulers) {
            Object instance = newInstance(scheduler);
            SchedulerProcessor.process(plugin, scheduler, instance);
        }
    }

    public void enable() {
        List<Class<?>> listeners = ReflectionUtil.findAnnotatedClasses(plugin, EventHandler.class);
        for (Class<?> listener : listeners) {
            Object instance = newInstance(listener);
            EventProcessor.process(plugin, listener, instance);
        }
        List<Class<?>> plugins = ReflectionUtil.findAnnotatedClasses(plugin, OnPluginEnable.class);
        for (Class<?> pluginClass : ReflectionUtil.findAnnotatedClasses(plugin, OnPluginDisable.class)) {
            if (!plugins.contains(pluginClass)) {
                plugins.add(pluginClass);
            }
        }
        for (Class<?> pluginClass : plugins) {
            Object instance = newInstance(pluginClass);
            pluginProcessor.process(pluginClass, instance);
        }
        pluginProcessor.enable(plugin);
    }

    public void disable() {
        AdvancedScheduler.cancelPluginTasks(plugin);
        CommandUtil.unregisterMyCommands();
        EventDispatcher.unregisterAll(plugin);
        pluginProcessor.disable(plugin);
    }

    public Object newInstance(Class<?> clazz) {
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
                Object fieldInstance = newInstance(field.getType());
                ReflectionUtil.setField(field, instance, fieldInstance);
            }
        }
        return instance;
    }
}

