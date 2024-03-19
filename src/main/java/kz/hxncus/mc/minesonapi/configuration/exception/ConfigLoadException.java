package kz.hxncus.mc.minesonapi.configuration.exception;

public class ConfigLoadException extends RuntimeException {

    public ConfigLoadException(Throwable cause) {
        this("An unexpected internal error was caught during (re-)loading the config.", cause);
    }

    public ConfigLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}