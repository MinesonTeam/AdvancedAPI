package kz.hxncus.mc.advancedapi.api.bukkit.command.info;


import kz.hxncus.mc.advancedapi.bukkit.command.CommandArguments;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ExecutionInfo {
	CommandSender getSender();
	Command getCommand();
	String getLabel();
	CommandArguments getArgs();
}
