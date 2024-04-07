package kz.hxncus.mc.minesonapi.bossbar.animation;

import kz.hxncus.mc.minesonapi.bossbar.MSBossBar;
import kz.hxncus.mc.minesonapi.scheduler.Schedule;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;

public interface BossBar {
    @NonNull Plugin getPlugin();
    @NonNull List<MSBossBar> getBossBarList();
    @NonNull BossBar addBossBar(@NonNull MSBossBar bossBar);
    @NonNull BossBar addBossBars(@NonNull List<MSBossBar> bossBars);
    @NonNull BossBar addBossBars(@NonNull MSBossBar...bossBars);
    @NonNull net.kyori.adventure.bossbar.BossBar replaceBossBar(MSBossBar bossBar);
    void startAnimation(Schedule schedule);
    void stopAnimation();
    @NonNull BossBar addPlayer(@NonNull Player player);
    @NonNull BossBar addPlayers(@NonNull Player...players);
    @NonNull BossBar addPlayers(@NonNull Collection<? extends Player> players);
    @NonNull BossBar removePlayer(@NonNull Player player);
    @NonNull BossBar removePlayers(@NonNull Player...players);
    @NonNull BossBar removePlayers(@NonNull Collection<? extends Player> players);
    @NonNull AnimationType getType();
}
