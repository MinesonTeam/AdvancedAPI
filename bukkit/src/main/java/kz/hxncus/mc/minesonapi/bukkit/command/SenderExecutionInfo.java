package kz.hxncus.mc.minesonapi.bukkit.command;

import kz.hxncus.mc.minesonapi.api.bukkit.command.ExecutionInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public record SenderExecutionInfo(CommandSender sender, Command command, String label, CommandArguments args) implements ExecutionInfo {
}
