package kz.hxncus.mc.minesonapi.bukkit.server;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Map;

@Getter
public class ServerManager {
    private static MinesonAPI plugin;
    private final Server server;

    public static final int MIN_COORD = -29999999;
    public static final int MAX_COORD = 29999999;

    public final Art[] arts = Art.values();
    public final Axis[] axes = Axis.values();
    public final ChatColor[] chatColors = ChatColor.values();
    public final EntityType[] entityTypes = EntityType.values();
    public final Material[] materials = Material.values();
    public final Particle[] particles = Particle.values();
    public final SoundCategory[] soundCategories = SoundCategory.values();
    public final Sound[] sounds = Sound.values();

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
}
