package kz.hxncus.mc.minesonapi.exception;

/**
 * The type Invalid command name exception.
 * @author Hxncus
 * @since  1.0.1
 */
public class InvalidCommandNameException extends RuntimeException {
	/**
	 * Instantiates a new Invalid command name exception.
	 *
	 * @param commandName the command name
	 */
	public InvalidCommandNameException(final String commandName) {
		super("Command name " + commandName + " is not a valid.");
	}
}
