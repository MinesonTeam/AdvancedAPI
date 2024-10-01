package kz.hxncus.mc.minesonapi.bukkit.config;

import kz.hxncus.mc.minesonapi.MinesonAPIPlugin;
import kz.hxncus.mc.minesonapi.utility.ColorUtil;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

/**
 * The enum Settings.
 * @since 1.0.1
 * @author Hxncus
 */
@Getter
public enum Settings {
	VERSION("version"),
	DEBUG("debug");
	private final String path;
	
	Settings(final String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return this.colorize((String) this.getSettings().get(this.path));
	}
	
	/**
	 * Colorize string.
	 *
	 * @param input the input
	 * @return the string
	 */
	public String colorize(final String input) {
		return ColorUtil.process(input);
	}
	
	/**
	 * Gets value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return this.getSettings()
		           .get(this.path);
	}
	
	/**
	 * Sets value.
	 *
	 * @param value the value
	 */
	public void setValue(final Object value) {
		this.setValue(value, true);
	}
	
	/**
	 * Sets value.
	 *
	 * @param value the value
	 * @param save  the save
	 */
	public void setValue(final Object value, final boolean save) {
		this.getSettings()
		    .set(this.path, value);
		final MinesonAPIPlugin plugin = MinesonAPIPlugin.getInstance();
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
	
	/**
	 * Gets settings.
	 *
	 * @return the settings
	 */
	public YamlConfiguration getSettings() {
		return MinesonAPIPlugin.getInstance()
		                       .getConfigService()
		                       .getOrCreateConfig("settings.yml");
	}
	
	/**
	 * To string string.
	 *
	 * @param def the def
	 * @return the string
	 */
	public String toString(final Object def) {
		return this.colorize((String) this.getValue(def));
	}
	
	/**
	 * Gets value.
	 *
	 * @param def the def
	 * @return the value
	 */
	public Object getValue(final Object def) {
		return this.getSettings()
		           .get(this.path, def);
	}
	
	/**
	 * To bool boolean.
	 *
	 * @return the boolean
	 */
	@SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
	public boolean toBool() {
		return (boolean) this.getValue();
	}
	
	/**
	 * To number.
	 *
	 * @return the number
	 */
	public Number toNumber() {
		return (Number) this.getValue();
	}
	
	/**
	 * To string a list.
	 *
	 * @return the list
	 */
	public List<String> toStringList() {
		final List<String> stringList = this.getSettings()
		                                    .getStringList(this.path);
		stringList.replaceAll(this::colorize);
		return stringList;
	}
	
	/**
	 * To config section configuration section.
	 *
	 * @return the configuration section
	 */
	public ConfigurationSection toConfigSection() {
		return this.getSettings()
		           .getConfigurationSection(this.path);
	}
}
