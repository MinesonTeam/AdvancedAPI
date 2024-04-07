package kz.hxncus.mc.minesonapi.bossbar.animation;

import kz.hxncus.mc.minesonapi.bossbar.MSBossBar;
import kz.hxncus.mc.minesonapi.scheduler.Schedule;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class StaticBossBar extends AnimatedBossBar {
    public StaticBossBar(@NonNull Plugin plugin, String name, long delay, long duration) {
        super(plugin, name, delay, duration, duration);
    }
    @Override
    public void startAnimation(Schedule schedule) {
        if (getAnimation() == null || getAnimation().isCancelled()) {
            List<MSBossBar> bossBarList = this.getBossBarList();
            if (bossBarList.isEmpty()) {
                return;
            }
            this.getPlayerList().forEach(player -> player.showBossBar(this.replaceBossBar(bossBarList.get(0))));
            setAnimation(schedule.timerN(bossBarList.size(), getDelay(), getPeriod(), n -> {
                this.getPlayerList().forEach(player -> player.showBossBar(this.replaceBossBar(bossBarList.get(n))));
            }));
        } else {
            throw new RuntimeException("BossBar animation is already running!");
        }
    }

    @Override
    public @NonNull AnimationType getType() {
        return AnimationType.STATIC;
    }
}
