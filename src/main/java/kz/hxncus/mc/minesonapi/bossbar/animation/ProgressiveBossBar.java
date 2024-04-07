package kz.hxncus.mc.minesonapi.bossbar.animation;

import kz.hxncus.mc.minesonapi.bossbar.MSBossBar;
import kz.hxncus.mc.minesonapi.scheduler.Schedule;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ProgressiveBossBar extends AnimatedBossBar {
    public ProgressiveBossBar(@NonNull Plugin plugin, String name, long delay, long period, long duration) {
        super(plugin, name, delay, period, duration);
    }

    @Override
    public void startAnimation(Schedule schedule) {
        if (getAnimation() == null || getAnimation().isCancelled()) {
            List<MSBossBar> bossBarList = this.getBossBarList();
            if (bossBarList.isEmpty()) {
                return;
            }
            this.getPlayerList().forEach(player -> player.showBossBar(this.replaceBossBar(bossBarList.get(0))));
            long durPer = getDuration() / getPeriod();
            setAnimation(schedule.timerN(durPer * getBossBarList().size(), getDelay(), getPeriod(), n -> {
                float progress = (n % durPer) / (100F / getPeriod());
                if (progress <= 0.0F || progress >= 1.0F) {
                    progress = 0.0F;
                    this.getPlayerList().forEach(player -> player.showBossBar(this.replaceBossBar(bossBarList.get((int) (n / durPer)))));
                }
                this.getBossBar().progress(progress);
            }));
        } else {
            throw new RuntimeException("BossBar animation is already running!");
        }
    }

    @Override
    public @NonNull AnimationType getType() {
        return AnimationType.PROGRESSIVE;
    }
}
