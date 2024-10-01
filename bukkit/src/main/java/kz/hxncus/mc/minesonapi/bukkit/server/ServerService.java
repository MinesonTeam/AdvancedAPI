package kz.hxncus.mc.minesonapi.bukkit.server;

import kz.hxncus.mc.minesonapi.MinesonAPIPlugin;
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
public class ServerService {
	private static MinesonAPIPlugin plugin;
	private final Server server;
	
	public ServerService(final MinesonAPIPlugin plugin, final Server server) {
		ServerService.plugin = plugin;
		this.server = server;
	}
	
	public Map<String, Command> getKnownCommands() {
		try {
			final SimpleCommandMap commandMap = this.getSimpleCommandMap();
			final Field knownCommandsField = commandMap.getClass()
			                                           .getDeclaredField("knownCommands");
			knownCommandsField.setAccessible(true);
			return (Map<String, Command>) knownCommandsField.get(commandMap);
		} catch (final NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public SimpleCommandMap getSimpleCommandMap() {
		try {
			final Field commandMapField = this.getSimplePluginManager()
			                                  .getClass()
			                                  .getDeclaredField("commandMap");
			commandMapField.setAccessible(true);
			return (SimpleCommandMap) commandMapField.get(this.getSimplePluginManager());
		} catch (final NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public SimplePluginManager getSimplePluginManager() {
		return (SimplePluginManager) this.server.getPluginManager();
	}
	
	public boolean isPaperServer() {
		if ("Paper"
				.equalsIgnoreCase(this.server.getName())) {
			return true;
		}
		try {
			Class.forName("com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent");
			return true;
		} catch (final ClassNotFoundException e) {
			return false;
		}
	}
	
	public boolean isFoliaServer() {
		return "Folia"
				.equalsIgnoreCase(this.server.getName());
	}
}
