package kz.hxncus.mc.advancedapi.storage;

import kz.hxncus.mc.advancedapi.api.bukkit.config.Config;
import kz.hxncus.mc.advancedapi.api.storage.AbstractStorage;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.Optional;

public class FileStorage<T> extends AbstractStorage<T> {
	private final Config config;
	private final String path;
	
	public FileStorage(Plugin plugin, Config config, String path) {
		super(plugin);
		this.config = config;
		this.path = path;
	}
	
	@Override
	public void init() {
		try {
			config.load(this.getPlugin());
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void shutdown() {
		try {
			config.save();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void save(final T thing) {
		try {
			config.setAndSave(path, thing);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Optional<T> load() {
		return Optional.empty();
	}
	
	@Override
	public void loadAll() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void delete() {
		this.config.set(this.path, null);
	}
}
