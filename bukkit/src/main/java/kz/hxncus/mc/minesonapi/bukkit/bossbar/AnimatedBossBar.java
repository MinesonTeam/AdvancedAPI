package kz.hxncus.mc.minesonapi.bukkit.bossbar;

import kz.hxncus.mc.minesonapi.bukkit.scheduler.Scheduler;

public class AnimatedBossBar extends SimpleBossBar implements Animatable {
    private final AnimationType animationType;
    private final long delay;
    private final long period;

    public AnimatedBossBar(AnimationType animationType, long delay, long period) {
        this.animationType = animationType;
        this.delay = delay;
        this.period = period;
    }

    @Override
    public void stopAnimation() {

    }

    @Override
    public void startAnimation(Scheduler scheduler) {
        Scheduler.timerAsync(0L, 20L, () -> {
            switch (animationType) {
                case STATIC:
                    startStaticAnimation();
                    break;
                case PROGRESSIVE:
                    startProgressiveAnimation();
                    break;
            }
        });
    }

    private void startStaticAnimation() {

    }

    private void startProgressiveAnimation() {

    }
}
