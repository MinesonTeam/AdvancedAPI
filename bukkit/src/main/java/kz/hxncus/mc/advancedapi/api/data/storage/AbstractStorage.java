package kz.hxncus.mc.advancedapi.api.data.storage;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

@Getter
public abstract class AbstractStorage<T> implements Storage<T> {
	private final Plugin plugin;
	
	protected AbstractStorage(Plugin plugin) {
		this.plugin = plugin;
	}
}
