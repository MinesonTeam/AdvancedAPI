package kz.hxncus.mc.advancedapi.api.bukkit.inventory.pagination;

import lombok.Getter;
import lombok.NonNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

@Getter
public abstract class AbstractPagination<T> implements Pagination<T> {
	protected T[] pages;
	private int size;
	
	protected AbstractPagination(final int size, @NonNull final Function<Integer, T[]> function) {
		this.size = size;
		this.pages = Collections.emptyList().toArray(function.apply(size));
	}
	
	@Override
	public Pagination<T> addPage(@NonNull final T page) {
		Pagination.super.addPage(page);
		return this;
	}
	
	@Override
	public Pagination<T> addPages(@NonNull final T[] pages) {
		Pagination.super.addPages(pages);
		return this;
	}
	
	@Override
	public Pagination<T> setPage(final int pageIndex, @NonNull final T page) {
		Pagination.super.setPage(pageIndex, page);
		return this;
	}
	
	@Override
	public Pagination<T> removePage(final int pageIndex) {
		Pagination.super.removePage(pageIndex);
		return this;
	}
	
	@Override
	public Pagination<T> removePage(@NonNull final T page) {
		Pagination.super.removePage(page);
		return this;
	}
	
	@Override
	public AbstractPagination<T> clear() {
		Pagination.super.clear();
		return this;
	}
	
	@Override
	public Pagination<T> setSize(final int size) {
		this.size = size;
		this.pages = Arrays.copyOf(this.pages, size);
		return this;
	}
}
