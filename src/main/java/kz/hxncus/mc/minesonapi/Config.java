package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.configuration.YamlTest;
import org.bukkit.plugin.Plugin;

public class Config extends YamlTest {
    @Ignore
    public static final Config CONFIG = new Config(MinesonAPI.getPlugin());
    public Config(Plugin plugin) {
        super(plugin);
    }
    public final COOLDOWN cooldown = new COOLDOWN();
    public static class COOLDOWN {
        @Comment("AirDrop summon delay in milliseconds")
        public int airDropSummonDelay = 3600000;
        @Comment("AirDrop unsummon delay after summon in ticks")
        public int airDropUnSummonDelay = 12000;
        @Comment("AirDrop remove after a time in ticks")
        public int airDropRemoveAfterTime = 12000;
        @Comment("AirDrop unlocking time in second")
        public int airDropUnlockingTime = 10;
        @Comment("AirDrop next summon time in milliseconds DONT TOUCH.")
        public long airDropNextSummonTime = 0;
    }
}
