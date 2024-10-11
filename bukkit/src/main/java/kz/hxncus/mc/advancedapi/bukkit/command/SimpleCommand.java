package kz.hxncus.mc.advancedapi.bukkit.command;

import kz.hxncus.mc.advancedapi.api.bukkit.command.AbstractCommand;
import lombok.ToString;

@ToString
public class SimpleCommand extends AbstractCommand {
	public SimpleCommand(final String commandName) {
		super(commandName);
	}
}
