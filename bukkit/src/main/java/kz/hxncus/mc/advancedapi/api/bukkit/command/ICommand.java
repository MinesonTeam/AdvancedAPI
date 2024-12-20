package kz.hxncus.mc.advancedapi.api.bukkit.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.google.common.base.Optional;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.Argument;
import kz.hxncus.mc.advancedapi.bukkit.command.CommandArguments;
import kz.hxncus.mc.advancedapi.bukkit.command.exception.CommandSyntaxException;
import kz.hxncus.mc.advancedapi.utility.CommandUtil;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ICommand extends CommandExecutor, TabCompleter {
	@NonNull String getName();
	String getPermission();
	String getPermissionMessage();
	@NonNull Command getCommand();
	@NonNull List<String> getAliases();

	ICommand description(String description);
	ICommand label(String label);
	ICommand permission(String permission);
	ICommand permissionMessage(String permission);
	ICommand usage(String permission);
	ICommand addAlias(String alias);
	ICommand addAliases(String... aliases);

	Map<String, ICommand> getSubCommands();
	List<Argument<?>> getArguments();

	List<kz.hxncus.mc.advancedapi.api.bukkit.command.CommandExecutor> getExecutors();
	List<kz.hxncus.mc.advancedapi.api.bukkit.command.TabCompleter> getCompleters();
	
	default ICommand getSubCommand(final String subCommandName) {
		for (ICommand subCommand : this.getSubCommands().values()) {
			if (subCommand.getName().equals(subCommandName) || subCommand.getAliases().contains(subCommandName)) {
				return subCommand;
			}
		}
		return null;
	}

	default Argument<?> getArgument(final String argumentName) {
		return this.getArguments().stream().filter(argument -> argument.getName().equals(argumentName)).findAny().get();
	}

	default boolean hasPermission(CommandSender sender) {
		return this.getPermission() != null && sender.hasPermission(this.getPermission());
	}

	default boolean onCommand(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
		ICommand command = this.getCommandFromArgs(args);
		if (!sender.isOp() && !hasPermission(sender)) {
			if (this.getPermissionMessage() == null) {
				sender.sendMessage(ChatColor.RED + "You don`t have permission to execute this command.");
			} else {
				sender.sendMessage(this.getPermissionMessage());
			}
			return false;
		}

		command.getExecutors().forEach(executor -> {
			try {
				executor.run(sender, command.getCommand(), label, new CommandArguments(this.convertArgs(command.getArguments(), this.getArgsWithoutSubCommands(args)), args));
			} catch (CommandSyntaxException ignored) {
			}
		});
		return true;
	}
	
	@Override
	default List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, @NonNull String[] args) {
		Optional<ICommand> tabCommand = this.getTabCommandFromArgs(args);
		if (!sender.isOp() && this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
			return Collections.emptyList();
		}
		return this.sort(this.filter(tabCommand.transform(icommand -> icommand.getCompleters().stream()
			.map(completer -> {
				try {
					return completer.run(sender, command, alias, args);
				} catch (CommandSyntaxException ignored) {
				}
				return Collections.<String>emptyList();
			}).flatMap(Collection::stream).collect(Collectors.toList())).or(Collections.emptyList()), args), args);
	}

	default Optional<ICommand> getTabCommandFromArgs(@NonNull final String[] args) {
		if (args == null || args.length <= 1) {
			return Optional.of(this);
		}
		int count = 1;
		ICommand current = this;
		for (String arg : args) {
			if (arg.isEmpty()) {
				continue;
			}
			ICommand subCommand = current.getSubCommand(arg.toLowerCase());
			if (subCommand == null) {
				break;
			}
			count++;
			current = subCommand;
		}
		if (args.length <= count) {
			return Optional.of(current);
		}
		return Optional.absent();
	}

	@NonNull
	default Object[] convertArgs(List<Argument<?>> arguments, String[] args) {
		Object[] convertedArgs = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			if (i >= arguments.size()) {
				break;
			}
			Argument<?> argument = arguments.get(i);
			if (argument == null) {
				break;
			}
			convertedArgs[i] = argument.parse(args[i]);
		}
		return convertedArgs;
	}

	@NonNull
	default String[] getArgsWithoutSubCommands(@NonNull final String[] args) {
		if (args == null || args.length <= 1) {
			return args;
		}
		int count = 0;
		ICommand current = this;
		for (String arg : args) {
			ICommand subCommand = current.getSubCommand(arg.toLowerCase());
			if (subCommand == null) {
				break;
			}
			current = subCommand;
			count += 1;
		}
		return Arrays.copyOfRange(args, count, args.length);
	}

	@NonNull
	default ICommand getCommandFromArgs(@NonNull final String[] args) {
		ICommand current = this;
		for (String arg : args) {
			ICommand subCommand = current.getSubCommand(arg.toLowerCase());
			if (subCommand == null) {
				break;
			}
			current = subCommand;
		}
		return current;
	}

	@Override
	default boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
		return this.onCommand(sender, label, args);
	}

	default ICommand subCommand(ICommand subCommand) {
		String subCommandName = subCommand.getName();

		this.getSubCommands().put(subCommandName, subCommand);
		this.complete((sender, command, alias, args) -> {
			if (sender.isOp() || subCommand.hasPermission(sender)) {
				List<String> list = new ArrayList<>(this.getAliases());
				list.add(subCommandName);
				return list;
			}
			return Collections.emptyList();
		});

		return this;
	}
	
	default ICommand subCommands(ICommand... subCommands) {
		Map<String, ICommand> map = List.of(subCommands).stream()
			.collect(Collectors.toMap(subCommand -> subCommand.getName(), subCommand -> subCommand));
		
		this.getSubCommands().putAll(map);
		this.complete((sender, command, alias, args) -> {
			if (sender.isOp() || Arrays.stream(subCommands).anyMatch(subCommand -> subCommand.hasPermission(sender))) {
				List<String> list = new ArrayList<>(this.getAliases());
				list.addAll(map.keySet());
				return list;
			}
			return Collections.emptyList();
		});

		return this;
	}
	
	default <T> ICommand argument(Argument<T> argument) {
		this.getArguments().add(argument);
		return this;
	}

	default ICommand arguments(Argument<?>... arguments) {
		this.getArguments().addAll(Arrays.asList(arguments));
		return this;
	}

	default ICommand execute(kz.hxncus.mc.advancedapi.api.bukkit.command.CommandExecutor executor) {
		this.getExecutors().add(executor);
		return this;
	}
	
	default ICommand complete(kz.hxncus.mc.advancedapi.api.bukkit.command.TabCompleter completer) {
		this.getCompleters().add(completer);
		return this;
	}
	
	default void register() {
		CommandUtil.registerCommand(this.getCommand(), true);
	}
	
	default void unregister() {
		CommandUtil.unregisterCommand(this.getCommand());
	}
	
	default List<String> filter(List<String> list, @NonNull String[] args) {
		if (args.length == 0 || list.isEmpty()) {
			return list;
		}
		String input = args[args.length - 1].toLowerCase();
		
		return list.stream()
			.filter(s -> s.toLowerCase().contains(input))
			.collect(Collectors.toList());
	}

	default List<String> sort(List<String> list, @NonNull String[] args) {
		if (args.length == 0 || list.isEmpty()) {
			return list;
		}
		String input = args[args.length - 1].toLowerCase();
		
		List<String> sorted = list.stream()
			.sorted((a, b) -> {
				// Сортируем по "близости" совпадения к началу строки
				int posA = a.toLowerCase().indexOf(input);
				int posB = b.toLowerCase().indexOf(input);
				return Integer.compare(posA, posB);
			})
			.collect(Collectors.toList());
		
		return sorted;
	}
}
