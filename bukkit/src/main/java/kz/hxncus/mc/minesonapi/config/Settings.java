package kz.hxncus.mc.minesonapi.config;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

@Getter
public enum Settings {
	VERSION("version"), DEBUG("debug");
	private final String path;
	
	Settings(final String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return this.colorize((String) this.getValue());
	}
	
	public String colorize(final String input) {
		return MinesonAPI.getInstance()
		                 .getColorManager()
		                 .process(input);
	}
	
	public Object getValue() {
		return this.getSettings()
		           .get(this.path);
	}
	
	public void setValue(final Object value) {
		this.setValue(value, true);
	}
	
	public void setValue(final Object value, final boolean save) {
		this.getSettings()
		    .set(this.path, value);
		final MinesonAPI plugin = MinesonAPI.getInstance();
		if (save) {
			try {
				this.getSettings()
				    .save(plugin.getDataFolder()
				                .toPath()
				                .resolve("settings.yml")
				                .toFile());
			} catch (final Exception e) {
				plugin.getLogger()
				      .severe("Failed to apply changes to settings.yml");
			}
		}
	}
	
	public YamlConfiguration getSettings() {
		return MinesonAPI.getInstance()
		                 .getConfigManager()
		                 .getOrCreateConfig("settings.yml");
	}
	
	public String toString(final Object def) {
		return this.colorize((String) this.getValue(def));
	}
	
	public Object getValue(final Object def) {
		return this.getSettings()
		           .get(this.path, def);
	}
	
	public boolean toBool() {
		return (boolean) this.getValue();
	}
	
	public Number toNumber() {
		return (Number) this.getValue();
	}
	
	public List<String> toStringList() {
		final List<String> stringList = this.getSettings()
		                                    .getStringList(this.path);
		stringList.replaceAll(this::colorize);
		return stringList;
	}
	
	public ConfigurationSection toConfigSection() {
		return this.getSettings()
		           .getConfigurationSection(this.path);
	}
}
