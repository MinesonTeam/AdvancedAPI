package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.color.ColorManager;
import kz.hxncus.mc.minesonapi.command.SimpleCommand;
import kz.hxncus.mc.minesonapi.command.argument.StringArgument;
import kz.hxncus.mc.minesonapi.config.ConfigManager;
import kz.hxncus.mc.minesonapi.event.EventManager;
import kz.hxncus.mc.minesonapi.inventory.InventoryManager;
import kz.hxncus.mc.minesonapi.inventory.SimpleInventory;
import kz.hxncus.mc.minesonapi.item.SimpleItem;
import kz.hxncus.mc.minesonapi.util.ScheduleUtil;
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
        SimpleInventory inventory = new SimpleInventory(54);
        inventory.setItem(0, new SimpleItem(Material.ACACIA_LOG));
    }

    @Override
    public void onDisable() {
        InventoryManager.closeAll();
        EventManager.unregisterAll();
        ScheduleUtil.cancelAllActiveSchedulers();
    }

    public void registerManagers() {
        this.colorManager = new ColorManager();
        this.configManager = new ConfigManager(this);
        this.eventManager = new EventManager(this);
        this.inventoryManager = new InventoryManager(this);
    }
}
