package kz.hxncus.mc.advancedapi.bukkit.command;

import com.google.common.base.Optional;

import lombok.NonNull;

public record CommandArguments(String[] input, Object[] args) {
	public Object get(final int index) {
		return this.args[index];
	}

	public <T> T getCasted(final int index) {
		Object obj = this.get(index);
		return obj == null ? null : (T) obj;
	}
	
	/**
	 * Получаем аргумент по индексу и значению по умолчанию
	 */
	public Object get(final int index, @NonNull Object def) {
		Object obj = this.get(index);
		return obj == null ? def : obj;
	}

	public <T> Object get(final int index, final Class<T> clazz) {
		return this.cast(this.get(index), clazz);
	}

	/**
	 * Получаем аргумент по индексу
	 */
	@NonNull
	public Optional<Object> getOptional(final int index) {
		return Optional.fromNullable(this.get(index));
	}

	@NonNull
	public <T> Optional<T> getOptionalCasted(final int index) {
		return Optional.fromNullable(this.getCasted(index));
	}

	/**
	 * Получаем аргумент по индексу и классу
	 */
	@NonNull
	public <T> Optional<T> getOptional(final int index, final Class<T> clazz) {
		return Optional.fromNullable(this.cast(this.get(index), clazz));
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
		return obj == null ? false : clazz.isInstance(obj);
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
