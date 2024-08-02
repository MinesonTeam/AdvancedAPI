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

    Settings(String path) {
        this.path = path;
    }

    public Object getValue() {
        return getSettings().get(path);
    }

    public Object getValue(Object def) {
        return getSettings().get(path, def);
    }

    public void setValue(Object value) {
        setValue(value, true);
    }

    public void setValue(Object value, boolean save) {
        getSettings().set(path, value);
        MinesonAPI plugin = MinesonAPI.get();
        if (save) {
            try {
                getSettings().save(plugin.getDataFolder().toPath().resolve("settings.yml").toFile());
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to apply changes to settings.yml");
            }
        }
    }

    @Override
    public String toString() {
        return colorize((String) getValue());
    }

    public String toString(Object def) {
        return colorize((String) getValue(def));
    }

    public String colorize(String input) {
        return MinesonAPI.get().getColorManager().process(input);
    }

    public boolean toBool() {
        return (boolean) getValue();
    }

    public Number toNumber() {
        return (Number) getValue();
    }

    public List<String> toStringList() {
        List<String> stringList = getSettings().getStringList(path);
        stringList.replaceAll(this::colorize);
        return stringList;
    }

    public ConfigurationSection toConfigSection() {
        return getSettings().getConfigurationSection(path);
    }

    public YamlConfiguration getSettings() {
        return MinesonAPI.get().getConfigManager().getOrCreateConfig("settings.yml");
    }
}
