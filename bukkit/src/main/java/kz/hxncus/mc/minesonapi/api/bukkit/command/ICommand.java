package kz.hxncus.mc.minesonapi.api.bukkit.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public interface ICommand extends CommandExecutor, TabCompleter {
	void register(JavaPlugin plugin);
	void unregister();
	String getCommandName();
	List<ICommand> getSubCommands();
	List<kz.hxncus.mc.minesonapi.api.bukkit.command.CommandExecutor> getExecutors();
	List<kz.hxncus.mc.minesonapi.api.bukkit.command.TabCompleter> getCompleters();
	ICommand subcommand(ICommand command);
	ICommand subcommands(ICommand... command);
	ICommand execute(kz.hxncus.mc.minesonapi.api.bukkit.command.CommandExecutor executor);
	ICommand complete(kz.hxncus.mc.minesonapi.api.bukkit.command.TabCompleter executor);
}
