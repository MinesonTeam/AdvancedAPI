package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

import kz.hxncus.mc.advancedapi.utility.ArrayUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

public abstract class AbstractPagination<T> implements Pagination<T> {
	@Getter
	private int size;
	@Getter
	protected T[] pages;
	
	protected AbstractPagination(final int size, Function<Integer, T[]> function) {
		this.size = size;
		this.pages = Collections.emptyList().toArray(function.apply(size));
	}
	
	@Override
	public Pagination<T> addPage(final T page) {
		for (int i = 0; i < pages.length; i++) {
			if (pages[i] == null) {
				pages[i] = page;
				return this;
			}
		}
		return this;
	}
	
	@Override
	public Pagination<T> addPages(final T... pages) {
		for (final T page : pages) {
			this.addPage(page);
		}
		return this;
	}
	
	@Override
	public Pagination<T> setPage(final int pageIndex, final T page) {
		this.pages[pageIndex] = page;
		return this;
	}
	
	@Override
	public Pagination<T> removePage(final int pageIndex) {
		this.pages[pageIndex] = null;
		return this;
	}
	
	@Override
	public Pagination<T> removePage(final T page) {
		this.pages[getPageIndex(page)] = null;
		return this;
	}
	
	@Override
	public T getNextPage(T page) {
		return this.getPage(this.getNextPageIndex(page));
	}
	
	@Override
	public T getPreviousPage(T page) {
		return this.getPage(this.getPreviousPageIndex(page));
	}
	
	@Override
	public Pagination<T> setSize(final int size) {
		this.size = size;
		this.pages = Arrays.copyOf(this.pages, size);
		return this;
	}
	
	@Override
	public int getPageIndex(final T page) {
		int pageIndex = ArrayUtil.indexOf(this.pages, page);
		return pageIndex;
	}
	
	@Override
	public int getNextPageIndex(T page) {
		int index = this.getPageIndex(page);
		if (index + 1 >= this.pages.length) {
			return 0;
		}
		return index + 1;
	}
	
	@Override
	public int getPreviousPageIndex(T page) {
		int index = this.getPageIndex(page);
		if (index - 1 < 0) {
			return this.pages.length - 1;
		}
		return index - 1;
	}
	
	@Override
	public T getPage(final int pageIndex) {
		return this.pages[pageIndex];
	}
	
	@Override
	public int getPageCount() {
		int count = 0;
		for (final T page : this.pages) {
			if (page == null) {
				break;
			}
			count++;
		}
		return count;
	}
	
	@Override
	public AbstractPagination<T> clear() {
		Arrays.fill(this.pages, null);
		return this;
	}
}
