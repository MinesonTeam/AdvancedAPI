package kz.hxncus.mc.advancedapi.api.bukkit.command;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.bukkit.command.CommandArguments;
import kz.hxncus.mc.advancedapi.exception.CommandSyntaxException;
import kz.hxncus.mc.advancedapi.utility.CommandUtil;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
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
	
	public boolean onCommand(int index, ICommand iCommand, @NonNull CommandSender sender, Command command, String label, String... args) {
		for (ICommand subCommand : iCommand.getSubCommands()) {
			if (args.length > index) {
				return this.onCommand(index + 1, subCommand, sender, command, label, args);
			} else if (args.length == index && args[index - 1].equalsIgnoreCase(subCommand.getCommandName())) {
				return execute(subCommand, sender, command, label, args);
			} else {
				return execute(this, sender, command, label, args);
			}
		}
		return false;
	}
	
	public boolean execute(ICommand iCommand, CommandSender sender, Command command, String label, String... args) {
		if (sender.hasPermission(iCommand.getPermission())) {
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
		return filter(onTabComplete(1, this, sender, command, alias, args), args);
	}
	
	public List<String> onTabComplete(int index, ICommand iCommand, @NonNull CommandSender sender, Command command, String alias, String... args) {
		List<String> result = new ArrayList<>();
		for (ICommand subCommand : iCommand.getSubCommands()) {
			if (args.length > index) {
				return this.onTabComplete(index + 1, subCommand, sender, command, alias, args);
			} else {
				if (args[index - 1].equalsIgnoreCase(subCommand.getCommandName())) {
					result.addAll(complete(subCommand.getCompleters(), sender, command, alias, args));
				}
				result.addAll(complete(this.completers, sender, command, alias, args));
			}
			result.add(subCommand.getCommandName());
		}
		return result;
	}
	
	public List<String> complete(List<TabCompleter> completers, CommandSender sender, Command command, String alias, String... args) {
		List<String> result = new ArrayList<>();
		for (TabCompleter completer : completers) {
			try {
				result.addAll(completer.run(sender, command, alias, new CommandArguments(args)));
			} catch (CommandSyntaxException ignored) {
			}
		}
		return result;
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
	
	public void register() {
		PluginCommand pluginCommand = AdvancedAPI.getInstance().getServer().getPluginCommand(this.commandName);
		if (pluginCommand != null) {
			pluginCommand.setExecutor(this);
			pluginCommand.setTabCompleter(this);
		}
//		CommandUtil.register(plugin, this.commandName);
	}
	
	public void unregister() {
		CommandUtil.unregister(this.commandName);
	}
}
