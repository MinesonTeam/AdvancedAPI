package kz.hxncus.mc.advancedapi.bukkit.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarHandler {
    public BossBar create(NamespacedKey key, String title, BarColor color, BarStyle style, BarFlag... barFlags) {
        return Bukkit.createBossBar(key, title, color, style, barFlags);
    }

    public boolean remove(NamespacedKey key) {
        return Bukkit.removeBossBar(key);
    }

    public boolean show(NamespacedKey key, Player player) {
        final BossBar bar = Bukkit.getBossBar(key);
        if (bar != null) {
            bar.addPlayer(player);
            return true;
        }
        return false;
    }

    public boolean showAll(NamespacedKey key) {
        final BossBar bar = Bukkit.getBossBar(key);
        if (bar != null) {
            Bukkit.getOnlinePlayers().forEach(bar::addPlayer);
            return true;
        }
        return false;
    }

    public boolean hide(NamespacedKey key, Player player) {
        final BossBar bar = Bukkit.getBossBar(key);
        if (bar != null) {
            bar.removePlayer(player);
            return true;
        }
        return false;
    }

    public boolean hideAll(NamespacedKey key) {
        final BossBar bar = Bukkit.getBossBar(key);
        if (bar != null) {
            Bukkit.getOnlinePlayers().forEach(bar::removePlayer);
            return true;
        }
        return false;
    }

    public boolean setTitle(NamespacedKey key, String title) {
        final BossBar bar = Bukkit.getBossBar(key);
        if (bar != null) {
            bar.setTitle(title);
            return true;
        }
        return false;
    }

    public boolean setColor(NamespacedKey key, BarColor color) {
        final BossBar bar = Bukkit.getBossBar(key);
        if (bar != null) {
            bar.setColor(color);
            return true;
        }
        return false;
    }

    public boolean setStyle(NamespacedKey key, BarStyle style) {
        final BossBar bar = Bukkit.getBossBar(key);
        if (bar != null) {
            bar.setStyle(style);
            return true;
        }
        return false;
    }

    public boolean addFlag(NamespacedKey key, BarFlag flag) {
        final BossBar bar = Bukkit.getBossBar(key);
        if (bar != null) {
            bar.addFlag(flag);
            return true;
        }
        return false;
    }

    public boolean setProgress(NamespacedKey key, double progress) {
        final BossBar bar = Bukkit.getBossBar(key);
        if (bar != null) {
            bar.setProgress(Math.max(0, Math.min(1, progress)));
            return true;
        }
        return false;
    }

    public void clear() {
        Bukkit.getBossBars().forEachRemaining(keyedBossBar -> Bukkit.removeBossBar(keyedBossBar.getKey()));
    }
}
