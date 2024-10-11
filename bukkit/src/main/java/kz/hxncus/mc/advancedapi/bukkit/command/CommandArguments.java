package kz.hxncus.mc.advancedapi.bukkit.command;

import java.util.Optional;

public record CommandArguments(Object[] args) {
	public Object get(final int index) {
		return args[index];
	}
	
	public Optional<Object> getOptional(final int index) {
		return Optional.ofNullable(args[index]);
	}
	
	public Object get(final int index, final Object def) {
		Object arg = args[index];
		return arg == null ? def : arg;
	}
	
	public <T> T get(final int index, final Class<T> clazz) {
		return clazz.cast(args[index]);
	}
	
	public <T> T get(final int index, final Class<T> clazz, T def) {
		Object arg = args[index];
		return clazz.isInstance(arg) ? clazz.cast(arg) : def;
	}
	
	public <T> Optional<T> getOptional(final int index, final Class<T> clazz) {
		return Optional.ofNullable(clazz.cast(args[index]));
	}
	
	public int length() {
		return args.length;
	}
}
