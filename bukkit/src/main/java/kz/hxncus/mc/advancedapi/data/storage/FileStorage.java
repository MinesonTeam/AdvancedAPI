package kz.hxncus.mc.advancedapi.data.storage;

import kz.hxncus.mc.advancedapi.api.data.storage.AbstractStorage;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileStorage<T> extends AbstractStorage<T> {
	private final FileConfiguration config;
	private final File file;
	private final String path;
	
	public FileStorage(Plugin plugin, FileConfiguration config, File file, String path) {
		super(plugin);
		this.config = config;
		this.file = file;
		this.path = path;
	}
	
	@Override
	public void init() {
		try {
			this.config.load(this.file);
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void shutdown() {
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void save(final T thing) {
		try {
			this.config.set(path, thing);
			this.config.save(this.file);
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
