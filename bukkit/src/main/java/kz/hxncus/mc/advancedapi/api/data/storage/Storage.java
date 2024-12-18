package kz.hxncus.mc.advancedapi.api.data.storage;

import org.bukkit.plugin.Plugin;

import com.google.common.base.Optional;

public interface Storage<T> {
	Plugin getPlugin();
	
	void init();
	
	void shutdown();
	
	void save(T thing);

	Optional<T> load();
	
	void loadAll();
	
	void delete();
}
