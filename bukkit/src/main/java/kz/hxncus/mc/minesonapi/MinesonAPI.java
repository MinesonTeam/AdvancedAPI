package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.bukkit.event.EventManager;
import kz.hxncus.mc.minesonapi.bukkit.inventory.InventoryManager;
import kz.hxncus.mc.minesonapi.bukkit.scheduler.Scheduler;
import kz.hxncus.mc.minesonapi.bukkit.server.ServerManager;
import kz.hxncus.mc.minesonapi.bukkit.workload.WorkloadRunnable;
import kz.hxncus.mc.minesonapi.bukkit.world.WorldManager;
import kz.hxncus.mc.minesonapi.color.ColorManager;
import kz.hxncus.mc.minesonapi.config.ConfigManager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MinesonAPI extends JavaPlugin {
    private static MinesonAPI instance;
    private ColorManager colorManager;
    private ConfigManager configManager;
    private EventManager eventManager;
    private InventoryManager inventoryManager;
    private ServerManager serverManager;
    private WorldManager worldManager;
    private WorkloadRunnable runnable;

    public MinesonAPI() {
        instance = this;
    }

    public static MinesonAPI get() {
        return instance;
    }

    public Location firstPos;
    public Location secondPos;

    @Override
    public void onEnable() {
        registerManagers();
        runnable = new WorkloadRunnable();
        Scheduler.timer(1L, 1L, runnable);
    }

    @Override
    public void onDisable() {
        InventoryManager.closeAll();
        EventManager.unregisterAll();
        Scheduler.stopAllTimers();
    }

    public void registerManagers() {
        this.colorManager = new ColorManager();
        this.configManager = new ConfigManager(this);
        this.eventManager = new EventManager(this);
        this.worldManager = new WorldManager(this);
        this.inventoryManager = new InventoryManager(this);
        this.serverManager = new ServerManager(this, getServer());
    }
}
