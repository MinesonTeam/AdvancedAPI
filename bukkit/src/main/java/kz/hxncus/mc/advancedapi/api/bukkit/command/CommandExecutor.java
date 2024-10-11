package kz.hxncus.mc.advancedapi.api.bukkit.command;

import kz.hxncus.mc.advancedapi.bukkit.command.CommandArguments;
import kz.hxncus.mc.advancedapi.bukkit.command.SenderExecutionInfo;
import kz.hxncus.mc.advancedapi.exception.CommandSyntaxException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface CommandExecutor {
	void run(CommandSender sender, Command command, String label, CommandArguments args) throws CommandSyntaxException;
	
	default void run(SenderExecutionInfo executionInfo) throws CommandSyntaxException {
		this.run(executionInfo.sender(), executionInfo.command(), executionInfo.label(), new CommandArguments(executionInfo.args().args()));
	}
}
