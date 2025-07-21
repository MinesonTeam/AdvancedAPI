package kz.hxncus.mc.advancedapi.bukkit.bossbar;

import kz.hxncus.mc.advancedapi.api.bukkit.bossbar.BossBarAnimation;
import org.bukkit.NamespacedKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BossBarSystem {
    private final Map<NamespacedKey, BossBarAnimation> animations = new ConcurrentHashMap<>();
    private final int updateTaskId = -1;
}
