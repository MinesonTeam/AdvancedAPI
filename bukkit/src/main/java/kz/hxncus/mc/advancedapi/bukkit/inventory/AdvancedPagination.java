package kz.hxncus.mc.advancedapi.bukkit.inventory;

import kz.hxncus.mc.advancedapi.api.bukkit.inventory.AbstractPagination;

import java.util.function.Function;

public class AdvancedPagination<T> extends AbstractPagination<T> {
	public AdvancedPagination(final int size, final Function<Integer, T[]> function) {
		super(size, function);
	}
}
