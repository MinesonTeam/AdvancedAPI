package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.inventory.MSInventoryManager;
import kz.hxncus.mc.minesonapi.listener.EventManager;
import kz.hxncus.mc.minesonapi.util.ScheduleUtil;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinesonAPI extends JavaPlugin {

    @Getter private static MinesonAPI plugin;
    @Getter
    private EventManager eventManager;

    @Override
    public void onEnable() {
        plugin = this;
        this.eventManager = new EventManager(MinesonAPI.getPlugin());
        MSInventoryManager.initialize();
    }

    @Override
    public void onDisable() {
        ScheduleUtil.cancelAllActiveSchedulers();
        MSInventoryManager.closeAll();
    }
}
