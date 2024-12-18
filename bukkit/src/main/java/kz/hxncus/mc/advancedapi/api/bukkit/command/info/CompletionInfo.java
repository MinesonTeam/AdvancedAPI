package kz.hxncus.mc.advancedapi.api.bukkit.command.info;

import kz.hxncus.mc.advancedapi.bukkit.command.CommandArguments;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CompletionInfo {
	CommandSender sender();
	Command command();
	String alias();
	CommandArguments args();
}
