package kz.hxncus.mc.advancedapi.api.bukkit.inventory;

public interface Pagination<T> {
	int getSize();
	
	Pagination<T> setSize(final int size);
	
	Pagination<T> addPage(final T page);
	
	Pagination<T> addPages(final T... pages);
	
	Pagination<T> setPage(final int pageIndex, final T page);
	
	Pagination<T> removePage(final int pageIndex);
	
	Pagination<T> removePage(final T page);
	
	int getPageIndex(T page);
	
	int getNextPageIndex(T page);
	
	int getPreviousPageIndex(T page);
	
	T getPage(final int pageIndex);
	
	T getNextPage(final T page);
	
	T getPreviousPage(final T page);
	
	int getPageCount();
	
	Pagination<T> clear();
	
	T[] getPages();
}
