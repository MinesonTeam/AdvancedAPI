package kz.hxncus.mc.advancedapi.bukkit.command.info;

import kz.hxncus.mc.advancedapi.api.bukkit.command.info.ExecutionInfo;
import kz.hxncus.mc.advancedapi.bukkit.command.CommandArguments;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public record SenderExecutionInfo(CommandSender sender, Command command, String label, CommandArguments args) implements ExecutionInfo {
}
