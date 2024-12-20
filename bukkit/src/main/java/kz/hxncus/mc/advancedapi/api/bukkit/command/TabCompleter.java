package kz.hxncus.mc.advancedapi.api.bukkit.command;

import kz.hxncus.mc.advancedapi.bukkit.command.exception.CommandSyntaxException;
import kz.hxncus.mc.advancedapi.bukkit.command.info.SenderExecutionInfo;
import lombok.NonNull;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collection;

@FunctionalInterface
public interface TabCompleter {
	Collection<String> run(CommandSender sender, Command command, String alias, String[] args) throws CommandSyntaxException;
	
	@NonNull
	default Collection<String> run(SenderExecutionInfo executionInfo) throws CommandSyntaxException {
		return this.run(executionInfo.sender(), executionInfo.command(), executionInfo.label(), executionInfo.args().getInput());
	}
}
