package kz.hxncus.mc.minesonapi.bossbar.animation;

import kz.hxncus.mc.minesonapi.bossbar.BossBarManager;
import kz.hxncus.mc.minesonapi.bossbar.MSBossBar;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@NoArgsConstructor
public abstract class AnimatedBossBar implements IBossBar {
    @Getter private final List<Player> playerList = new ArrayList<>();
    @Getter private final List<MSBossBar> bossBarList = new ArrayList<>();
    @Getter private final BossBar bossBar = BossBar.bossBar(Component.text(), 1F, BossBar.Color.PURPLE, BossBar.Overlay.PROGRESS);
    @Getter @Setter private BukkitTask animation;
    @Getter private Plugin plugin;
    @Getter private String name;
    @Getter private long delay;
    @Getter private long period;
    @Getter private long duration;
    protected AnimatedBossBar(@NonNull Plugin plugin, String name, long delay, long period, long duration) {
        this.plugin = plugin;
        this.name = name;
        this.delay = delay;
        this.period = period;
        this.duration = duration;
    }
    @Override
    public @NonNull BossBar replaceBossBar(@NonNull MSBossBar bossBar) {
        this.bossBar.name(Component.text(bossBar.name())).color(bossBar.color()).progress(bossBar.progress()).overlay(bossBar.overlay());
        return this.bossBar;
    }

    @Override
    public void stopAnimation() {
        if (this.animation == null) {
            return;
        }
        this.animation.cancel();
        this.playerList.forEach(player -> player.hideBossBar(this.bossBar));
    }
    @Override
    public @NonNull IBossBar addPlayer(@NonNull Player player) {
        this.playerList.add(player);
        return this;
    }
    @Override
    public @NonNull IBossBar addPlayers(@NonNull Player...players) {
        this.playerList.addAll(List.of(players));
        return this;
    }
    @Override
    public @NonNull IBossBar addPlayers(@NonNull Collection<? extends Player> players) {
        this.playerList.addAll(players);
        return this;
    }
    @Override
    public @NonNull IBossBar removePlayer(@NonNull Player player) {
        this.playerList.remove(player);
        return this;
    }

    @Override
    public @NonNull IBossBar removePlayers(@NonNull Player...players) {
        this.playerList.removeAll(List.of(players));
        return this;
    }
    @Override
    public @NonNull IBossBar removePlayers(@NonNull Collection<? extends Player> players) {
        this.playerList.removeAll(players);
        return this;
    }
    @Override
    public @NonNull IBossBar addBossBar(@NonNull MSBossBar bossBar) {
        this.bossBarList.add(bossBar);
        return this;
    }

    @Override
    public @NonNull IBossBar addBossBars(@NotNull @NonNull List<MSBossBar> bossBars) {
        this.bossBarList.addAll(bossBars);
        return this;
    }

    @Override
    public @NonNull IBossBar addBossBars(@NonNull MSBossBar...bossBars) {
        this.bossBarList.addAll(List.of(bossBars));
        return this;
    }
}





















