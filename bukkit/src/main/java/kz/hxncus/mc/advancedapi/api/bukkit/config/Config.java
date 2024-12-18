package kz.hxncus.mc.advancedapi.api.bukkit.config;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public interface Config extends ConfigurationSection {
	File getFile();

	String getFileName();

	boolean isFileExists();
	
	boolean createFile() throws IOException;
	
	boolean load() throws IOException, InvalidConfigurationException;
	
	boolean loadDefaults(Plugin plugin) throws IOException;
	
	boolean load(Plugin plugin) throws IOException, InvalidConfigurationException;
	
	void save() throws IOException;
	
	void setAndSave(@NonNull final String path, @NonNull final Object value) throws IOException;
}