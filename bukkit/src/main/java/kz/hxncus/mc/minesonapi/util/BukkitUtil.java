package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import lombok.experimental.UtilityClass;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Map;

@UtilityClass
public class BukkitUtil {
    public SimplePluginManager getSimplePluginManager() {
        return (SimplePluginManager) MinesonAPI.get().getServer().getPluginManager();
    }

    public SimpleCommandMap getSimpleCommandMap() {
        try {
            SimplePluginManager spm = getSimplePluginManager();
            Field commandMapField = spm.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (SimpleCommandMap) commandMapField.get(spm);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Command> getKnownCommands() {
        try {
            SimpleCommandMap commandMap = getSimpleCommandMap();
            Field knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            return (Map<String, Command>) knownCommandsField.get(commandMap);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
