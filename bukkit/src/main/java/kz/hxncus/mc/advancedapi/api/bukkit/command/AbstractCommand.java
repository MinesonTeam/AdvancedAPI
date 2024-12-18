package kz.hxncus.mc.advancedapi.api.bukkit.command;

import lombok.Getter;
import lombok.NonNull;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.Argument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class AbstractCommand extends Command implements ICommand {
	protected final Map<String, ICommand> subCommands = new HashMap<>();
	protected final List<Argument<?>> arguments = new ArrayList<>();
	protected final List<CommandExecutor> executors = new ArrayList<>();
	protected final List<TabCompleter> completers = new ArrayList<>();

	protected AbstractCommand(final String name) {
		super(name.toLowerCase());
	}

	protected AbstractCommand(@NonNull String name, @NonNull String description, @NonNull String usageMessage, @NonNull List<String> aliases) {
		super(name.toLowerCase(), description, usageMessage, aliases);
	}

	@Override
	public boolean execute(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
		if (this.onCommand(sender, this, label, args)) {
            return true;
		}
		sender.sendMessage(ChatColor.RED + getUsage());
        return false;
	}

	@NonNull
	@Override
    public List<String> tabComplete(@NonNull CommandSender sender, @NonNull String alias, @NonNull String[] args) throws IllegalArgumentException {
		return this.onTabComplete(sender, this, alias, args);
    }

	@Override
	public Command getCommand() {
		return this;
	}

	@Override
	public ICommand description(String description) {
		this.setDescription(description);
		return this;
	}

	@Override
	public ICommand label(String label) {
		this.setLabel(label);
		return this;
	}

	@Override
	public ICommand permission(String permission) {
		this.setPermission(permission);
		return this;
	}

	@Override
	public ICommand permissionMessage(String permission) {
		this.setPermissionMessage(permission);
		return this;
	}

	@Override
	public ICommand usage(String permission) {
		this.setUsage(permission);
		return this;
	}

	@Override
	public ICommand addAliases(String... aliases) {
		Collections.addAll(super.getAliases(), aliases);
		return this;
	}

	@Override
	public ICommand addAlias(String alias) {
		super.getAliases().add(alias);
		return this;
	}
}
