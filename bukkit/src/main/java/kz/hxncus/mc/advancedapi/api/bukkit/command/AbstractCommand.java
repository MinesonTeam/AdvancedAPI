package kz.hxncus.mc.advancedapi.api.bukkit.command;

import kz.hxncus.mc.advancedapi.bukkit.command.CommandArguments;
import kz.hxncus.mc.advancedapi.exception.CommandSyntaxException;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Getter
public abstract class AbstractCommand implements ICommand {
	private final String commandName;
	private String permission;
	private final List<ICommand> subCommands = new ArrayList<>();
	private final List<CommandExecutor> executors = new ArrayList<>();
	private final List<TabCompleter> completers = new ArrayList<>();
	
	protected AbstractCommand(String commandName) {
		this.commandName = commandName;
	}
	
	@Override
	public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull... args) {
		return this.onCommand(1, this, sender, command, label, args);
	}
	
	private boolean onCommand(int index, ICommand iCommand, @NonNull CommandSender sender, Command command, String label, String... args) {
		List<ICommand> commandSubCommands = iCommand.getSubCommands();
		if (commandSubCommands.isEmpty() || args.length == 0) {
			return execute(iCommand, sender, command, label, args);
		}
		for (ICommand subCommand : commandSubCommands) {
			if (args[index - 1].equalsIgnoreCase(subCommand.getCommandName())) {
				if (args.length > index) {
					return this.onCommand(index + 1, subCommand, sender, command, label, args);
				} else {
					return execute(subCommand, sender, command, label, args);
				}
			} else {
				return execute(this, sender, command, label, args);
			}
		}
		return false;
	}
	
	private boolean execute(ICommand iCommand, CommandSender sender, Command command, String label, String... args) {
		if (iCommand.getPermission() == null || sender.hasPermission(iCommand.getPermission())) {
			for (CommandExecutor executor : iCommand.getExecutors()) {
				try {
					executor.run(sender, command, label, new CommandArguments(args));
				} catch (CommandSyntaxException ignored) {
				}
			}
			return true;
		} else {
			sender.sendMessage("§cYou don't have permission to execute this command!");
			return false;
		}
	}
	
	@Override
	public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String @NonNull ... args) {
		return filter(onTabComplete(2, this, sender, command, alias, args), args);
	}
	
	private List<String> onTabComplete(int index, ICommand iCommand, @NonNull CommandSender sender, Command command, String alias, String... args) {
		List<String> result = new ArrayList<>();
		List<ICommand> commandSubCommands = iCommand.getSubCommands();
		for (ICommand subCommand : commandSubCommands) {
			if (subCommand.getCommandName().equalsIgnoreCase(args[index - 2])) {
				if (args.length + 1 > index) {
					return this.onTabComplete(index + 1, subCommand, sender, command, alias, args);
				} else {
					result.addAll(complete(subCommand, sender, command, alias, args));
				}
			} else {
				result.add(subCommand.getCommandName());
			}
		}
		result.addAll(complete(iCommand, sender, command, alias, args));
		return result;
	}
	
	private List<String> complete(ICommand iCommand, CommandSender sender, Command command, String alias, String... args) {
		if (iCommand.getPermission() == null || sender.hasPermission(iCommand.getPermission())) {
			List<String> result = new ArrayList<>();
			for (TabCompleter completer : iCommand.getCompleters()) {
				try {
					result.addAll(completer.run(sender, command, alias, new CommandArguments(args)));
				} catch (CommandSyntaxException ignored) {
				}
			}
			return result;
		} else {
			return Collections.emptyList();
		}
	}
	
	private List<String> filter(List<String> list, String... args) {
		String last = args[args.length - 1];
		List<String> result = new ArrayList<>();
		for (final String arg : list) {
			if (arg.toLowerCase(Locale.ENGLISH).startsWith(last.toLowerCase(Locale.ENGLISH))) {
				result.add(arg);
			}
		}
		return result;
	}
	
	public ICommand subcommand(ICommand command) {
		this.subCommands.add(command);
		return this;
	}
	
	public ICommand subcommands(ICommand... command) {
		this.subCommands.addAll(List.of(command));
		return this;
	}
	
	public ICommand execute(CommandExecutor executor) {
		this.executors.add(executor);
		return this;
	}
	
	public ICommand complete(TabCompleter completer) {
		this.completers.add(completer);
		return this;
	}
	
	public ICommand permission(String permission) {
		this.permission = permission;
		return this;
	}
	
	public void register(JavaPlugin plugin) {
		PluginCommand command = plugin.getCommand(this.commandName);
		if (command != null) {
			command.setExecutor(this);
			command.setTabCompleter(this);
		}
	}
	
	public void unregister(JavaPlugin plugin) {
		PluginCommand command = plugin.getCommand(this.commandName);
		if (command != null) {
			command.setExecutor(null);
			command.setTabCompleter(null);
		}
	}
}
