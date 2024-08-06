package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.bukkit.event.EventManager;
import kz.hxncus.mc.minesonapi.bukkit.inventory.InventoryManager;
import kz.hxncus.mc.minesonapi.bukkit.scheduler.Scheduler;
import kz.hxncus.mc.minesonapi.bukkit.server.ServerManager;
import kz.hxncus.mc.minesonapi.bukkit.world.WorldManager;
import kz.hxncus.mc.minesonapi.color.ColorManager;
import kz.hxncus.mc.minesonapi.config.ConfigManager;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The type Mineson api.
 */
@Getter
@ToString
public class MinesonAPI extends JavaPlugin {
	@Getter
	private static MinesonAPI instance;
	private ColorManager colorManager;
	private ConfigManager configManager;
	private EventManager eventManager;
	private InventoryManager inventoryManager;
	private ServerManager serverManager;
	private WorldManager worldManager;
	
	/**
	 * Instantiates a new Mineson api.
	 */
	public MinesonAPI() {
		instance = this;
	}
	
	@Override
	public void onDisable() {
		Scheduler.stopTimers();
		this.inventoryManager.closeAll();
		this.eventManager.unregisterAll();
	}
	
	@Override
	public void onEnable() {
		this.registerManagers();
	}
	
	public void registerManagers() {
		this.colorManager = new ColorManager();
		this.configManager = new ConfigManager(this);
		this.eventManager = new EventManager(this);
		this.worldManager = new WorldManager(this);
		this.inventoryManager = new InventoryManager(this);
		this.serverManager = new ServerManager(this, this.getServer());
	}
}
