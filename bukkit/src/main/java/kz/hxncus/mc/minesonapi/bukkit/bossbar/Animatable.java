package kz.hxncus.mc.minesonapi.bukkit.bossbar;

import kz.hxncus.mc.minesonapi.bukkit.scheduler.Scheduler;

public interface Animatable {
    void stopAnimation();
    void startAnimation(Scheduler scheduler);
    enum AnimationType {
        STATIC,
        PROGRESSIVE,
    }
}
