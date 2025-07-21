package kz.hxncus.mc.advancedapi.bukkit.command;

import kz.hxncus.mc.advancedapi.api.bukkit.command.AbstractCommand;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;

@ToString
public class AdvancedCommand extends AbstractCommand {
	public AdvancedCommand(final String name) {
		super(name);
	}

	public AdvancedCommand(String name, String permission) {
		super(name, permission);
	}

	public AdvancedCommand(@NonNull String name, @NonNull String description, @NonNull String usageMessage, @NonNull List<String> aliases) {
		super(name, description, usageMessage, aliases);
	}
}
