package kz.hxncus.mc.minesonapi.exception;

public class InvalidCommandNameException extends RuntimeException {
	public InvalidCommandNameException(final String commandName) {
		super("Command name " + commandName + " is not a valid.");
	}
}
