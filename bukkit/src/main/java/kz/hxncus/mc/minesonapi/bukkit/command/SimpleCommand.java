package kz.hxncus.mc.minesonapi.bukkit.command;

import kz.hxncus.mc.minesonapi.api.bukkit.command.AbstractCommand;
import lombok.ToString;

@ToString
public class SimpleCommand extends AbstractCommand {
	public SimpleCommand(final String commandName) {
		super(commandName);
	}
}
