package kz.hxncus.mc.advancedapi.bukkit.command;

import kz.hxncus.mc.advancedapi.annotation.TabValues;
import kz.hxncus.mc.advancedapi.api.bukkit.command.provider.TabProvider;
import kz.hxncus.mc.advancedapi.utility.ReflectionUtil;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

public class CommandEntry {
    public final String[] path;
    public final Method method;
    public final Object instance;

    public CommandEntry(String[] path, Method method, Object instance) {
        this.path = path;
        this.method = method;
        this.instance = instance;
    }

    public boolean matches(String[] args) {
        if (args.length != path.length) {
            return false;
        }
        for (int i = 0; i < args.length; i++) {
            if (!args[i].equalsIgnoreCase(path[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean isPrefix(String[] args) {
        for (int i = 0; i < Math.min(args.length, path.length); i++) {
            if (!path[i].toLowerCase().startsWith(args[i].toLowerCase())) return false;
        }
        return true;
    }

    public boolean invoke(CommandSender sender, String[] args) {
        ReflectionUtil.invoke(method, instance, sender, args);
        return true;
    }

    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        int offset = path.length;
        int targetIndex = args.length - 1;

        Parameter[] parameters = method.getParameters();

        int argParamIndex = targetIndex - offset;
        if (argParamIndex >= parameters.length || argParamIndex < 0) {
            return Collections.emptyList();
        }

        Parameter param = parameters[argParamIndex];
        if (!param.isAnnotationPresent(TabValues.class)) {
            return Collections.emptyList();
        }

        TabValues tab = param.getAnnotation(TabValues.class);
        try {
            TabProvider provider = tab.value().getDeclaredConstructor().newInstance();
            return provider.provide(sender);
        } catch (Exception ignored) {
            // Ignored
        }

        return Collections.emptyList();
    }
}
