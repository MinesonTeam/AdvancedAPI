package kz.hxncus.mc.advancedapi.api.bukkit.command;


import kz.hxncus.mc.advancedapi.bukkit.command.CommandArguments;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ExecutionInfo {
	CommandSender sender();
	Command command();
	String label();
	CommandArguments args();
}
