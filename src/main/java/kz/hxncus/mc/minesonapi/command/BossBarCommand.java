package kz.hxncus.mc.minesonapi.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BossBarCommand extends Command {

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {

    }

    @Override
    public List<String> complete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
