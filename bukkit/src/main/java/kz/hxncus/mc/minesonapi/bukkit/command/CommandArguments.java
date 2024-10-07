package kz.hxncus.mc.minesonapi.bukkit.command;

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
	
	public <T> Optional<T> getOptional(final int index, final Class<T> clazz) {
		return Optional.ofNullable(clazz.cast(args[index]));
	}
	
	public int length() {
		return args.length;
	}
}
