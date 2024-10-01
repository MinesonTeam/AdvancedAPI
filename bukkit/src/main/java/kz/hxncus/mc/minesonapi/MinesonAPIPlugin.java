package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.api.MinesonAPI;
import kz.hxncus.mc.minesonapi.bukkit.config.ConfigService;
import kz.hxncus.mc.minesonapi.bukkit.event.EventService;
import kz.hxncus.mc.minesonapi.bukkit.inventory.InventoryManager;
import kz.hxncus.mc.minesonapi.bukkit.scheduler.Scheduler;
import kz.hxncus.mc.minesonapi.bukkit.server.ServerService;
import kz.hxncus.mc.minesonapi.bukkit.world.WorldService;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The type Mineson api.
 * @author Hxncus
 * @since 1.0.0
 */
@Getter
@ToString
public class MinesonAPIPlugin extends JavaPlugin implements MinesonAPI {
	@Getter
	private static MinesonAPIPlugin instance;
	private ConfigService configService;
	private EventService eventService;
	private InventoryManager inventoryService;
	private ServerService serverService;
	private WorldService worldService;
	
	/**
	 * Instantiates a new Mineson api.
	 */
	public MinesonAPIPlugin() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		this.registerServices();
	}
	
	@Override
	public void onDisable() {
		Scheduler.stopTimers();
		this.inventoryService.closeAll();
		this.eventService.unregisterAll();
	}
	
	private void registerServices() {
		this.configService = new ConfigService(this);
		this.eventService = new EventService(this);
		this.worldService = new WorldService(this);
		this.inventoryService = new InventoryManager(this);
		this.serverService = new ServerService(this, this.getServer());
	}
}