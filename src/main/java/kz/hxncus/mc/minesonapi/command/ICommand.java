package kz.hxncus.mc.minesonapi.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICommand {
    void execute(@NotNull CommandSender sender, @NotNull String label, String[] args);

    List<String> complete(CommandSender sender, String label, String[] args);
}
