package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.bossbar.BossBarManager;
import kz.hxncus.mc.minesonapi.inventory.DupeFixer;
import kz.hxncus.mc.minesonapi.inventory.MSInventoryManager;
import kz.hxncus.mc.minesonapi.listener.EventManager;
import kz.hxncus.mc.minesonapi.listener.PluginDisablingEvent;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinesonAPI extends JavaPlugin {

    @Getter private static MinesonAPI plugin;

    public MinesonAPI() {
        plugin = this;
    }

    @Getter private DupeFixer dupeFixer;
    @Getter private MSInventoryManager inventoryManager;
    @Getter private BossBarManager bossBarManager;
    @Override
    public void onEnable() {
        this.dupeFixer = new DupeFixer(this);
        this.inventoryManager = new MSInventoryManager(this);
        this.bossBarManager = new BossBarManager(this);
    }

    @Override
    public void onDisable() {
        Config.COOLDOWN cooldown = Config.CONFIG.cooldown;
        EventManager.getInstance(MinesonAPI.getPlugin()).callEvent(new PluginDisablingEvent(this));
    }
}
