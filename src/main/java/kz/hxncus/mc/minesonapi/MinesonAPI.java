package kz.hxncus.mc.minesonapi;

import org.bukkit.plugin.java.JavaPlugin;

public final class MinesonAPI extends JavaPlugin {
    private static MinesonAPI instance;
    public static MinesonAPI getInstance() {
        return instance;
    }
    public MinesonAPI() {
        instance = this;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
