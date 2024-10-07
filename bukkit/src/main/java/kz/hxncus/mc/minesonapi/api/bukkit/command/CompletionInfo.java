package kz.hxncus.mc.minesonapi.api.bukkit.command;

import kz.hxncus.mc.minesonapi.bukkit.command.CommandArguments;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CompletionInfo {
	CommandSender sender();
	Command command();
	String alias();
	CommandArguments args();
}
