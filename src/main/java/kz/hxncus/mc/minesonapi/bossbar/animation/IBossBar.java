package kz.hxncus.mc.minesonapi.bossbar.animation;

import kz.hxncus.mc.minesonapi.bossbar.MSBossBar;
import kz.hxncus.mc.minesonapi.scheduler.Schedule;
import lombok.NonNull;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;

public interface IBossBar {
    @NonNull Plugin getPlugin();
    @NonNull List<MSBossBar> getBossBarList();
    @NonNull IBossBar addBossBar(@NonNull MSBossBar bossBar);
    @NonNull IBossBar addBossBars(@NonNull List<MSBossBar> bossBars);
    @NonNull IBossBar addBossBars(@NonNull MSBossBar...bossBars);
    @NonNull BossBar replaceBossBar(MSBossBar bossBar);
    void startAnimation(Schedule schedule);
    void stopAnimation();
    @NonNull IBossBar addPlayer(@NonNull Player player);
    @NonNull IBossBar addPlayers(@NonNull Player...players);
    @NonNull IBossBar addPlayers(@NonNull Collection<? extends Player> players);
    @NonNull IBossBar removePlayer(@NonNull Player player);
    @NonNull IBossBar removePlayers(@NonNull Player...players);
    @NonNull IBossBar removePlayers(@NonNull Collection<? extends Player> players);
    @NonNull AnimationType getType();
}
