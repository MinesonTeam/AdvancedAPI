package kz.hxncus.mc.minesonapi.bukkit.server;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class ServerManager {
    private static MinesonAPI plugin;
    private final Server server;

    public ServerManager(MinesonAPI plugin, Server server) {
        ServerManager.plugin = plugin;
        this.server = server;
    }

    public SimplePluginManager getSimplePluginManager() {
        return (SimplePluginManager) server.getPluginManager();
    }

    public SimpleCommandMap getSimpleCommandMap() {
        try {
            Field commandMapField = getSimplePluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (SimpleCommandMap) commandMapField.get(getSimplePluginManager());
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

    public boolean isPaperServer() {
        if (server.getName().equalsIgnoreCase("Paper")) {
            return true;
        }
        try {
            Class.forName("com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public boolean isFoliaServer() {
        return server.getName().equalsIgnoreCase("Folia");
    }
}
