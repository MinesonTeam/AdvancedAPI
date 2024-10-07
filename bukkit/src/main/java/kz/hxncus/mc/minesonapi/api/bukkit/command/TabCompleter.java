package kz.hxncus.mc.minesonapi.api.bukkit.command;

import kz.hxncus.mc.minesonapi.bukkit.command.CommandArguments;
import kz.hxncus.mc.minesonapi.bukkit.command.SenderExecutionInfo;
import kz.hxncus.mc.minesonapi.exception.CommandSyntaxException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

@FunctionalInterface
public interface TabCompleter {
	List<String> run(CommandSender sender, Command command, String alias, CommandArguments args) throws CommandSyntaxException;
	
	default List<String> run(SenderExecutionInfo executionInfo) throws CommandSyntaxException {
		return this.run(executionInfo.sender(), executionInfo.command(), executionInfo.label(), new CommandArguments(executionInfo.args().args()));
	}
}
