package kz.hxncus.mc.minesonapi.utility;

import kz.hxncus.mc.minesonapi.utility.reflect.ReflectionUtil;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@UtilityClass
public class CommandUtil {
	private final String FIELD_COMMAND_MAP = "commandMap";
	private final String FIELD_KNOWN_COMMANDS = "knownCommands";
	private final SimpleCommandMap COMMAND_MAP = getCommandMap();
	
	private SimpleCommandMap getCommandMap() {
		return (SimpleCommandMap) ReflectionUtil.getFieldValue(Bukkit.getServer(), FIELD_COMMAND_MAP);
	}
	
	public boolean register(@NonNull JavaPlugin plugin, @NonNull String commandName) {
		PluginCommand command = plugin.getServer().getPluginCommand(commandName);
		if (command == null) {
			return false;
		}
		return CommandUtil.register(plugin, command);
	}
	
	public void postRegister(Plugin plugin) {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			player.updateCommands();
		}
	}
	
	public boolean register(@NonNull Plugin plugin, @NonNull Command command) {
		boolean isRegistered = COMMAND_MAP.register(plugin.getName(), command);
		postRegister(plugin);
		return isRegistered;
	}
	
	@SuppressWarnings("unchecked")
	public boolean unregister(@NonNull String name) {
		Command command = getCommand(name).orElse(null);
		if (command == null) {
			return false;
		}
		Map<String, Command> knownCommands = (HashMap<String, Command>) ReflectionUtil.getFieldValue(COMMAND_MAP, FIELD_KNOWN_COMMANDS);
		if (knownCommands == null) {
			return false;
		} else if (!command.unregister(COMMAND_MAP)) {
			return false;
		}
		return knownCommands.keySet().removeIf(key -> key.equalsIgnoreCase(command.getName()) || command.getAliases().contains(key));
	}
	
	@NonNull
	public Set<String> getAliases(@NonNull String name) {
		return getAliases(name, false);
	}
	
	@NonNull
	public Set<String> getAliases(@NonNull String name, boolean inclusive) {
		Command command = getCommand(name).orElse(null);
		if (command == null) return Collections.emptySet();
		
		Set<String> aliases = new HashSet<>(command.getAliases());
		if (inclusive) aliases.add(command.getName());
		return aliases;
	}
	
	@NonNull
	public Optional<Command> getCommand(@NonNull String name) {
		return COMMAND_MAP.getCommands().stream()
		                  .filter(command -> command.getName().equalsIgnoreCase(name) || command.getLabel().equalsIgnoreCase(name) || command.getAliases().contains(name))
		                  .findFirst();
	}
	
//	@NonNull
//	public String getCommandName(@NonNull String str) {
//		String name = ColorService.strip(str).split(" ")[0].substring(1);
//
//		String[] pluginPrefix = name.split(":");
//		if (pluginPrefix.length == 2) {
//			name = pluginPrefix[1];
//		}
//
//		return name;
//	}
//
//	public Player getPlayerOrSender(@NonNull CommandContext context, @NonNull ParsedArguments arguments, @NonNull String name) {
//		Player player;
//		if (arguments.hasArgument(name)) {
//			player = arguments.getPlayerArgument(name);
//		}
//		else {
//			if (context.getExecutor() == null) {
//				context.errorPlayerOnly();
//				return null;
//			}
//			player = context.getExecutor();
//		}
//		return player;
//	}
}
