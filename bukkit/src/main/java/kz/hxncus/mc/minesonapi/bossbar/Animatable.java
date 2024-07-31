package kz.hxncus.mc.minesonapi.bossbar;

import kz.hxncus.mc.minesonapi.scheduler.Schedule;

public interface Animatable {
    void stopAnimation();
    void startAnimation(Schedule schedule);
    enum AnimationType {
        STATIC,
        PROGRESSIVE,
    }
}
