package kz.hxncus.mc.advancedapi.bukkit.command;

import kz.hxncus.mc.advancedapi.api.bukkit.command.CompletionInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public record SenderCompletionInfo(CommandSender sender, Command command, String alias, CommandArguments args) implements CompletionInfo {
}
