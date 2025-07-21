package kz.hxncus.mc.advancedapi.api.bukkit.command.provider;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface TabProvider {
    List<String> provide(CommandSender sender);
}

