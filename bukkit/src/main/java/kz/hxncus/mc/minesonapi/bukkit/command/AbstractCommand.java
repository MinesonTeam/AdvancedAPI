package kz.hxncus.mc.minesonapi.bukkit.command;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.bukkit.command.argument.Argument;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.*;

/**
 * The type Abstract command.
 */
@Getter
@ToString
public abstract class AbstractCommand implements ICommand {
	private final CommandMeta commandMeta;
	private final List<Argument> arguments = new ArrayList<>(5);
	private final List<ICommand> subcommands = new ArrayList<>(10);
	
	/**
	 * Instantiates a new Abstract command.
	 *
	 * @param commandName the command name
	 */
	protected AbstractCommand(final String commandName) {
		this.commandMeta = new CommandMeta(commandName);
	}
	
	/**
	 * With arguments abstract command.
	 *
	 * @param args the args
	 * @return the abstract command
	 */
	public AbstractCommand withArguments(final Collection<? extends Argument> args) {
		this.arguments.addAll(args);
		return this;
	}
	
	/**
	 * With arguments abstract command.
	 *
	 * @param args the args
	 * @return the abstract command
	 */
	public AbstractCommand withArguments(final Argument... args) {
		this.arguments.addAll(Arrays.asList(args));
		return this;
	}
	
	/**
	 * With optional arguments abstract command.
	 *
	 * @param args the args
	 * @return the abstract command
	 */
	public AbstractCommand withOptionalArguments(final Iterable<? extends Argument> args) {
		for (final Argument arg : args) {
			arg.setOptional(true);
			this.arguments.add(arg);
		}
		return this;
	}
	
	/**
	 * With optional arguments abstract command.
	 *
	 * @param args the args
	 * @return the abstract command
	 */
	public AbstractCommand withOptionalArguments(final Argument... args) {
		for (final Argument arg : args) {
			arg.setOptional(true);
			this.arguments.add(arg);
		}
		return this;
	}
	
	@Override
	public boolean onCommand(@NonNull final CommandSender sender, @NonNull final Command command,
	                         @NonNull final String label, final String @NonNull [] args) {
		return true;
	}
	
	@Override
	public List<String> onTabComplete(@NonNull final CommandSender sender, @NonNull final Command command,
	                                  @NonNull final String label, final String @NonNull [] args) {
		return Collections.emptyList();
	}
	
	/**
	 * Register command.
	 *
	 * @param plugin the plugin
	 */
	public void register(final MinesonAPI plugin) {
		plugin.getServerManager()
		      .getKnownCommands()
		      .putIfAbsent(this.commandMeta.getCommandName(), null);
	}
}