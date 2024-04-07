package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.bossbar.BossBarManager;
import kz.hxncus.mc.minesonapi.configuration.Yaml;
import kz.hxncus.mc.minesonapi.inventory.MSInventoryManager;
import kz.hxncus.mc.minesonapi.scheduler.ScheduleManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinesonAPI extends JavaPlugin {

    @Getter private static MinesonAPI plugin;

    public MinesonAPI() {
        plugin = this;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        ScheduleManager.getInstance().removeSchedules(plugin);
        MSInventoryManager.getInstance().closeAll();
        BossBarManager.getInstance().disableManager(plugin);
        Yaml.removeYamls(plugin);
    }
}
