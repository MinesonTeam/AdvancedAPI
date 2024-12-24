package kz.hxncus.mc.advancedapi.api.bukkit.command.info;

import kz.hxncus.mc.advancedapi.bukkit.command.CommandArguments;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CompletionInfo {
	CommandSender getSender();
	Command getCommand();
	String getAlias();
	CommandArguments getArgs();
}
