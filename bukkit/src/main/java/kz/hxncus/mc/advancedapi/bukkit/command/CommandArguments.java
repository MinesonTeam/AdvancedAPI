package kz.hxncus.mc.advancedapi.bukkit.command;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.Argument;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class CommandArguments {
	private Object[] args;
	private String[] input;
	private List<Argument> arguments;

	public CommandArguments(final Object[] args, final String[] input, final List<Argument> arguments) {
		this.args = args;
		this.input = input;
		this.arguments = arguments;
	}

	public Object get(final int index) {
		try {
			return this.args[index];
		} catch (IndexOutOfBoundsException ignored) {
			// ignored
		}
		return null;
	}
	
	/**
	 * Получаем аргумент по индексу и значению по умолчанию
	 */
	public Object get(final int index, @NonNull Object def) {
		Object obj = this.get(index);
		return obj == null ? def : obj;
	}

	public <T> T get(final int index, final Class<T> clazz) {
		return this.cast(this.get(index), clazz);
	}

	public Object get(final String nodeName) {
		for (int i = 0; i < this.arguments.size(); i++) {
			Argument arg = this.arguments.get(i);
			if (arg.getNodeName().equals(nodeName)) {
				return this.cast(this.args[i], arg.getType());
			}
		}
		return null;
	}

	/**
	 * Получаем аргумент по индексу
	 */
	@NonNull
	public Optional<Object> getOptional(final int index) {
		return Optional.ofNullable(this.get(index));
	}

	/**
	 * Получаем аргумент по индексу и классу
	 */
	@NonNull
	public <T> Optional<T> getOptional(final int index, final Class<T> clazz) {
		return Optional.ofNullable(this.cast(this.get(index), clazz));
	}
	
	/**
	 * Получаем аргумент по индексу и классу
	 */
	@NonNull
	public <T> T get(final int index, final Class<T> clazz, @NonNull T def) {
		T casted = this.cast(this.get(index), clazz);
		return casted == null ? def : casted;
	}
	
	/**
	 * Проверяем, является ли аргумент экземпляром класса
	 */
	private <T> boolean isInstance(final Object obj, final Class<T> clazz) {
		return clazz.isInstance(obj);
	}
	
	/**
	 * Приводим аргумент к классу
	 */
	private <T> T cast(final Object obj, final Class<T> clazz) {
		if (this.isInstance(obj, clazz)) {
			return clazz.cast(obj);
		}
		return null;
	}

	/**
	 * Получаем длину аргументов
	 */
	public int count() {
		return args.length;
	}

	/**
	 * Получаем длину аргументов
	 */
	public int length() {
		return args.length;
	}
}
