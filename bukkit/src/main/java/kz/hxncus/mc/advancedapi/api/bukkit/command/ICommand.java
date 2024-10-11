package kz.hxncus.mc.advancedapi.api.bukkit.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import java.util.List;

public interface ICommand extends CommandExecutor, TabCompleter {
	String getCommandName();
	String getPermission();
	ICommand permission(String permission);
	List<ICommand> getSubCommands();
	List<kz.hxncus.mc.advancedapi.api.bukkit.command.CommandExecutor> getExecutors();
	List<kz.hxncus.mc.advancedapi.api.bukkit.command.TabCompleter> getCompleters();
	ICommand subcommand(ICommand command);
	ICommand subcommands(ICommand... command);
	ICommand execute(kz.hxncus.mc.advancedapi.api.bukkit.command.CommandExecutor executor);
	ICommand complete(kz.hxncus.mc.advancedapi.api.bukkit.command.TabCompleter executor);
	void register();
	void unregister();
}
