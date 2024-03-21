package kz.hxncus.mc.minesonapi.command;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Command implements ICommand, CommandExecutor, TabCompleter {
    /**
     * Gets the class name and removes the
     * end to create a new command.
     * For example, "TestCommand" -> "/test"
     */
    protected Command() {
        PluginCommand command = MinesonAPI.getInstance().getCommand(StringUtils.removeEnd(getClass().getSimpleName(), "Command"));
        if (command != null) {
            command.setExecutor(this);
            command.setTabCompleter(this);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        execute(sender, label, args);
        return true;
    }

    private List<String> filter(List<String> list, String[] args) {
        if (list == null) return Collections.emptyList();
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for (String arg : list) {
            if (arg.toLowerCase()
                   .startsWith(last.toLowerCase())) result.add(arg);
        }
        return result;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return filter(complete(sender, label, args), args);
    }
}
