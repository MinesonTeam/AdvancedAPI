package kz.hxncus.mc.minesonapi.bossbar.animation;

import kz.hxncus.mc.minesonapi.bossbar.AnimatedBossBar;
import kz.hxncus.mc.minesonapi.bossbar.AnimationType;
import lombok.NonNull;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Set;

public class StaticBossBar extends AnimatedBossBar {
    public StaticBossBar(@NonNull Plugin plugin, int delay, int duration) {
        super(plugin, delay, duration, duration);
    }
    @Override
    public void startAnimation() {
        if (getAnimation() == null) {
            List<BossBar> bossBarList = this.getBossBarList();
            if (bossBarList.isEmpty()) {
                throw new RuntimeException("BossBarList is empty!");
            }
            this.getPlayerList().forEach(player -> player.showBossBar(this.replaceBossBar(bossBarList.get(0))));
            setAnimation(new BukkitRunnable() {
                int index = 0;
                @Override
                public void run() {
                    getPlayerList().forEach(player -> player.showBossBar(replaceBossBar(bossBarList.get(index++ % bossBarList.size()))));
                    if (index == Integer.MAX_VALUE) {
                        index = Integer.MIN_VALUE;
                    }
                }
            });
        } else if (!getAnimation().isCancelled()) {
            throw new RuntimeException("BossBar animation is already running!");
        }
        getAnimation().runTaskTimer(getPlugin(), getDelay(), getDuration());
    }

    @Override
    public @NonNull AnimationType getType() {
        return AnimationType.STATIC;
    }
}
