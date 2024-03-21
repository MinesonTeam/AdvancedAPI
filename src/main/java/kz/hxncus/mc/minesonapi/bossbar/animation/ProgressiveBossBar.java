package kz.hxncus.mc.minesonapi.bossbar.animation;

import kz.hxncus.mc.minesonapi.bossbar.AnimatedBossBar;
import kz.hxncus.mc.minesonapi.bossbar.AnimationType;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ProgressiveBossBar extends AnimatedBossBar {
    public ProgressiveBossBar(@NonNull Plugin plugin, int delay, int period, int duration) {
        super(plugin, delay, period, duration);
    }

    @Override
    public void startAnimation() {
        if (getAnimation() == null) {
            setAnimation(new BukkitRunnable() {
                int i = 0;
                int j = 0;
                @Override
                public void run() {

                }
            });
        } else if (!getAnimation().isCancelled()) {
            throw new RuntimeException("BossBar animation is already running!");
        }
        getAnimation().runTaskTimer(getPlugin(), getDelay(), getPeriod());
    }

    @Override
    public @NonNull AnimationType getType() {
        return AnimationType.PROGRESSIVE;
    }
}
