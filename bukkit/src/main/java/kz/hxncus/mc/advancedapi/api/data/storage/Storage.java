package kz.hxncus.mc.advancedapi.api.data.storage;

import org.bukkit.plugin.Plugin;

import java.util.Optional;

public interface Storage<T> {
	Plugin getPlugin();
	
	void init();
	
	void shutdown();
	
	void save(T thing);

	Optional<T> load();
	
	void loadAll();
	
	void delete();
}
