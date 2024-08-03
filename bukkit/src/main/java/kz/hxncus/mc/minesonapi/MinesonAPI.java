package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.bukkit.command.SimpleCommand;
import kz.hxncus.mc.minesonapi.bukkit.command.argument.StringArgument;
import kz.hxncus.mc.minesonapi.bukkit.event.EventManager;
import kz.hxncus.mc.minesonapi.bukkit.inventory.InventoryManager;
import kz.hxncus.mc.minesonapi.bukkit.inventory.SimpleInventory;
import kz.hxncus.mc.minesonapi.bukkit.item.SimpleItem;
import kz.hxncus.mc.minesonapi.bukkit.scheduler.Scheduler;
import kz.hxncus.mc.minesonapi.bukkit.server.ServerManager;
import kz.hxncus.mc.minesonapi.bukkit.world.WorldManager;
import kz.hxncus.mc.minesonapi.color.ColorManager;
import kz.hxncus.mc.minesonapi.config.ConfigManager;
import lombok.Getter;
import org.bukkit.Material;
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

    public MinesonAPI() {
        instance = this;
    }

    public static MinesonAPI get() {
        return instance;
    }

    @Override
    public void onEnable() {
        new SimpleCommand("test").withArguments(new StringArgument("player_name"));
        registerManagers();
        SimpleInventory inventory = new SimpleInventory(54).setItem(0, new SimpleItem(Material.ACACIA_LOG).displayName("Next page").addLore("321", "123").apply(), (page, event) -> {
            page.getPage(0).open(event.getWhoClicked());
        });
        inventory.addPage(new SimpleInventory(54).setItem(0, new SimpleItem(Material.ACACIA_BOAT).displayName("Previous page").apply(), (page, event) -> {
            page.getPage(0).open(event.getWhoClicked());
        }));
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
