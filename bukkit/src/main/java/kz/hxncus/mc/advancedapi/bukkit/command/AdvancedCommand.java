package kz.hxncus.mc.advancedapi.bukkit.command;

import java.util.List;

import kz.hxncus.mc.advancedapi.api.bukkit.command.AbstractCommand;
import lombok.NonNull;
import lombok.ToString;

@ToString
public class AdvancedCommand extends AbstractCommand {
	public AdvancedCommand(final String commandName) {
		super(commandName);
	}

	public AdvancedCommand(@NonNull String name, @NonNull String description, @NonNull String usageMessage, @NonNull List<String> aliases) {
		super(name, description, usageMessage, aliases);
	}
}
