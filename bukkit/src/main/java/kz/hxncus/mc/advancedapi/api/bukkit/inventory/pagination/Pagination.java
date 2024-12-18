package kz.hxncus.mc.advancedapi.api.bukkit.inventory.pagination;

import java.util.Arrays;

import kz.hxncus.mc.advancedapi.utility.ArrayUtil;
import lombok.NonNull;

public interface Pagination<T> {
	int getSize();
	
	Pagination<T> setSize(final int size);
	
	default Pagination<T> addPage(@NonNull final T page) {
		for (int i = 0; i < this.getPages().length; i++) {
			if (this.getPages()[i] == null) {
				this.getPages()[i] = page;
				return this;
			}
		}
		return this;
	}
	
	default Pagination<T> addPages(@NonNull final T[] pages) {
		for (final T page : pages) {
			this.addPage(page);
		}
		return this;
	}
	
	default Pagination<T> setPage(final int pageIndex, @NonNull final T page) {
		this.getPages()[pageIndex] = page;
		return this;
	}
	
	default Pagination<T> removePage(final int pageIndex) {
		this.getPages()[pageIndex] = null;
		return this;
	}
	
	default Pagination<T> removePage(@NonNull final T page) {
		this.getPages()[getPageIndex(page)] = null;
		return this;
	}
	
	default int getPageIndex(@NonNull final T page) {
		return ArrayUtil.indexOf(this.getPages(), page);
	}
	
	default int getNextPageIndex(@NonNull final T page) {
		int index = this.getPageIndex(page);
		if (index + 1 >= this.getPages().length) {
			return 0;
		}
		return index + 1;
	}
	
	default int getPreviousPageIndex(@NonNull final T page) {
		int index = this.getPageIndex(page);
		if (index - 1 < 0) {
			return this.getPages().length - 1;
		}
		return index - 1;
	}
	
	default T getPage(final int pageIndex) {
		return this.getPages()[pageIndex];
	}
	
	default int getPageCount() {
		int count = 0;
		for (final T page : this.getPages()) {
			if (page == null) {
				break;
			}
			count++;
		}
		return count;
	}
	
	default T getNextPage(@NonNull final T page) {
		return this.getPage(this.getNextPageIndex(page));
	}
	
	default T getPreviousPage(@NonNull final T page) {
		return this.getPage(this.getPreviousPageIndex(page));
	}
	
	default Pagination<T> clear() {
		Arrays.fill(this.getPages(), null);
		return this;
	}
	
	@NonNull
	T[] getPages();
}
