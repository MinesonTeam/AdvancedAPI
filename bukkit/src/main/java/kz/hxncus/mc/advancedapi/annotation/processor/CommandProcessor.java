package kz.hxncus.mc.advancedapi.annotation.processor;

import kz.hxncus.mc.advancedapi.annotation.*;
import kz.hxncus.mc.advancedapi.bukkit.command.CommandEntry;
import kz.hxncus.mc.advancedapi.bukkit.scheduler.AdvancedScheduler;
import kz.hxncus.mc.advancedapi.utility.CommandUtil;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.*;

@UtilityClass
public class CommandProcessor {
    private final Map<String, List<CommandEntry>> commands = new HashMap<>();
    private final Map<UUID, Map<Method, Long>> cooldowns = new HashMap<>();

    public void register(Class<?> clazz, Object obj) {
        Command command = clazz.getAnnotation(Command.class);
        if (command == null) {
            return;
        }
        boolean async = clazz.isAnnotationPresent(Async.class);
        CommandAlias commandAlias = clazz.getAnnotation(CommandAlias.class);
        RequirePlayer requirePlayer = clazz.getAnnotation(RequirePlayer.class);
        RequireConsole requireConsole = clazz.getAnnotation(RequireConsole.class);
        RequireWorld requireWorld = clazz.getAnnotation(RequireWorld.class);
        RequirePermission requirePermission = clazz.getAnnotation(RequirePermission.class);
        org.bukkit.command.Command bukkitCommand = new org.bukkit.command.Command(command.name()) {
            @Override
            public boolean execute(CommandSender sender, String alias, String[] args) {
                if (requirePlayer != null && !(sender instanceof Player)) {
                    sender.sendMessage(requirePlayer.value());
                    return true;
                } else if (requireConsole != null && !(sender instanceof ConsoleCommandSender)) {
                    sender.sendMessage(requireConsole.value());
                    return true;
                }
                if (requirePlayer != null && requireWorld != null && !((Player) sender).getWorld().getName().equals(requireWorld.value())) {
                    sender.sendMessage(requireWorld.message());
                    return true;
                }
                if (async) {
                    AdvancedScheduler.runAsync(() -> onCommand(sender, this, args));
                    return true;
                }
                return onCommand(sender, this, args);
            }

            @Override
            public List<String> tabComplete(@NonNull CommandSender sender, @NonNull String alias, @NonNull String[] args) {
                return onTabComplete(sender, this, args);
            }
        };
        if (commandAlias != null && commandAlias.value() != null) {
            bukkitCommand.setAliases(Arrays.asList(commandAlias.value()));
        }
        if (command.description() != null && !command.description().isEmpty()) {
            bukkitCommand.setDescription(command.description());
        }
        if (command.usage() != null && !command.usage().isEmpty()) {
            bukkitCommand.setUsage(command.usage());
        }
        if (requirePermission != null && requirePermission.value() != null) {
            bukkitCommand.setPermission(requirePermission.value());
        }
        CommandUtil.registerCommand(bukkitCommand, true);
        for (Method method : clazz.getDeclaredMethods()) {
            Subcommand subcommand = method.getAnnotation(Subcommand.class);
            if (subcommand == null) {
                continue;
            }
            List<String[]> paths = new ArrayList<>();
            String subcommandValue = subcommand.value();
            paths.add(subcommandValue.split(" "));
            if (method.isAnnotationPresent(CommandAlias.class)) {
                for (String alias : method.getAnnotation(CommandAlias.class).value()) {
                    paths.add(alias.split(" "));
                }
            }
            List<CommandEntry> commandEntries = new ArrayList<>();
            for (String[] path : paths) {
                commandEntries.add(new CommandEntry(path, method, obj));
            }
            commands.put(command.name(), commandEntries);
        }
    }

    private boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String[] args) {
        List<CommandEntry> entries = commands.get(command.getName());
        if (entries == null || entries.isEmpty()) {
            return false;
        }
        for (CommandEntry entry : entries) {
            if (entry.matches(args)) {
                Method method = entry.method;
                CommandCooldown commandCooldown = method.getAnnotation(CommandCooldown.class);
                if (commandCooldown != null && sender instanceof Player) {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    cooldowns.putIfAbsent(uuid, new HashMap<>());
                    Map<Method, Long> userCooldowns = cooldowns.get(uuid);
                    long lastUsed = userCooldowns.getOrDefault(method, 0L);
                    long currentTimeMillis = System.currentTimeMillis();
                    if ((currentTimeMillis - lastUsed) < commandCooldown.value()) {
                        long remaining = (commandCooldown.value() - (currentTimeMillis - lastUsed)) / 1000;
                        player.sendMessage(commandCooldown.message().replace("%time%", String.valueOf(remaining)));
                        return true;
                    }
                    userCooldowns.put(method, currentTimeMillis);
                }
                return entry.invoke(sender, args);
            }
        }
        return true;
    }

    private List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String[] args) {
        List<String> result = new ArrayList<>();
        List<CommandEntry> entries = commands.get(command.getName());
        if (entries == null) {
            return result;
        }
        for (CommandEntry entry : entries) {
            if (entry.isPrefix(args)) {
                result.addAll(entry.getTabCompletions(sender, args));
            }
        }
        return result;
    }
}
