package kz.hxncus.mc.advancedapi.bukkit.config.property;

import kz.hxncus.mc.advancedapi.api.bukkit.config.Config;
import kz.hxncus.mc.advancedapi.api.bukkit.config.property.ListProperty;

import java.util.List;

public class StringListProperty extends ListProperty<String> {
	public StringListProperty(final Config config, final String path) {
		super(config, path);
	}
	
	public StringListProperty(final Config config, final String path, final List<String> defaultValue) {
		super(config, path, defaultValue);
	}
	
	@Override
	public List<String> getValue() {
		Config config = this.getConfig();
		String path = this.getPath();
		if (!config.contains(path)) {
			return this.getDefaultValue();
		}
		List<String> stringList = config.getStringList(path);
		if (stringList.isEmpty() && this.getDefaultValue() != null) {
			return this.getDefaultValue();
		}
		return stringList;
	}
}
