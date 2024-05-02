package kz.hxncus.mc.minesonapi.bossbar;

import kz.hxncus.mc.minesonapi.scheduler.Schedule;
import org.bukkit.entity.Player;

public interface BossBar {
    void stopAnimation();
    void startAnimation(Schedule schedule);
    void addPlayer(Player player);
    void removePlayer(Player player);
    void setAnimationType(AnimationType type);
    enum AnimationType {
        STATIC,
        PROGRESSIVE,
    }
}
