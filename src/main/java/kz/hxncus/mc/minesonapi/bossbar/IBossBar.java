package kz.hxncus.mc.minesonapi.bossbar;

import lombok.NonNull;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface IBossBar {
    AnimatedBossBar addBossBar(@NonNull BossBar bossBar);
    AnimatedBossBar addBossBars(@NonNull BossBar...bossBars);
    BossBar replaceBossBar(BossBar bossBar);
    void startAnimation();
    void stopAnimation();
    AnimatedBossBar addPlayer(@NonNull Player player);
    AnimatedBossBar addPlayers(@NonNull Player...players);
    AnimatedBossBar addPlayers(@NonNull Collection<? extends Player> players);
    AnimatedBossBar removePlayer(@NonNull Player player);
    AnimatedBossBar removePlayers(@NonNull Player...players);
    AnimatedBossBar removePlayers(@NonNull Collection<? extends Player> players);
    @NonNull AnimationType getType();
}
