package kz.hxncus.mc.advancedapi.bukkit.inventory.pagination;

import java.util.function.Function;

import kz.hxncus.mc.advancedapi.api.bukkit.inventory.pagination.AbstractPagination;

public class AdvancedPagination<T> extends AbstractPagination<T> {
	public AdvancedPagination(final int size, final Function<Integer, T[]> function) {
		super(size, function);
	}
}
