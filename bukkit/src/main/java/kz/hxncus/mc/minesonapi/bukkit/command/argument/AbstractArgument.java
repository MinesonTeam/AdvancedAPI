package kz.hxncus.mc.minesonapi.bukkit.command.argument;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Abstract argument.
 */
@ToString
public abstract class AbstractArgument implements Argument {
	private final String nodeName;
	private final Set<String> suggestions = new HashSet<>(100);
	@Getter
	@Setter
	private boolean optional;
	
	protected AbstractArgument(final String nodeName) {
		this.nodeName = nodeName;
	}
}