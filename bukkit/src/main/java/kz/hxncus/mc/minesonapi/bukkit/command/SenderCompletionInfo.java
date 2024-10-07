package kz.hxncus.mc.minesonapi.bukkit.command;

import kz.hxncus.mc.minesonapi.api.bukkit.command.CompletionInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public record SenderCompletionInfo(CommandSender sender, Command command, String alias, CommandArguments args) implements CompletionInfo {
}
