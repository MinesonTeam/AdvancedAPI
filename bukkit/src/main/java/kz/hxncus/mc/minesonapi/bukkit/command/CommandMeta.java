package kz.hxncus.mc.minesonapi.bukkit.command;

import kz.hxncus.mc.minesonapi.exception.InvalidCommandNameException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.command.CommandSender;

import java.util.function.Predicate;

/**
 * The type Command meta.
 */
@Getter
@ToString
public class CommandMeta {
	private final String commandName;
	@Setter
	private String permission;
	@Setter
	private String[] aliases;
	@Setter
	private Predicate<CommandSender> requirements;
	@Setter
	private String shortDescription;
	@Setter
	private String fullDescription;
	@Setter
	private String[] usageDescription;
	@Setter
	private String helpTopic;
	
	/**
	 * Instantiates a new Command meta.
	 *
	 * @param commandName the command name
	 */
	public CommandMeta(final String commandName) {
		this.setPermission("");
		this.setAliases(new String[8]);
		this.setRequirements(commandSender -> true);
		this.setShortDescription("");
		this.setFullDescription("");
		this.setUsageDescription(new String[4]);
		this.setHelpTopic("");
		if (commandName != null && !commandName.isEmpty() && !commandName.contains(" ")) {
			this.commandName = commandName;
		} else {
			throw new InvalidCommandNameException(commandName);
		}
	}
}
