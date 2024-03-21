package kz.hxncus.mc.minesonapi.bossbar;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AnimatedBossBar implements IBossBar {
    @Getter
    private final List<Player> playerList = new ArrayList<>();
    @Getter
    private final List<BossBar> bossBarList = new ArrayList<>();
    @Getter
    private BossBar bossBar = BossBar.bossBar(Component.text(), 1F, BossBar.Color.PURPLE, BossBar.Overlay.PROGRESS);
    @Getter
    @Setter
    private BukkitRunnable animation;
    @Getter
    private final Plugin plugin;
    @Getter
    private final int delay;
    @Getter
    private final int period;
    @Getter
    private final int duration;
    protected AnimatedBossBar(@NonNull Plugin plugin, int delay, int period, int duration) {
        this.plugin = plugin;
        this.delay = delay;
        this.period = period;
        this.duration = duration;
    }
    @Override
    public BossBar replaceBossBar(BossBar bossBar) {
        this.bossBar.name(bossBar.name()).color(bossBar.color()).progress(bossBar.progress()).overlay(bossBar.overlay());
        return this.bossBar;
    }

    @Override
    public void stopAnimation() {
        if (this.animation == null) {
            throw new RuntimeException("BossBar animation is null.");
        } else if (this.animation.isCancelled()) {
            throw new RuntimeException("BossBar animation is already stopped!");
        }
        this.animation.cancel();
    }
    @Override
    public AnimatedBossBar addPlayer(@NonNull Player player) {
        this.playerList.add(player);
        return this;
    }
    @Override
    public AnimatedBossBar addPlayers(@NonNull Player...players) {
        this.playerList.addAll(List.of(players));
        return this;
    }
    @Override
    public AnimatedBossBar addPlayers(@NonNull Collection<? extends Player> players) {
        this.playerList.addAll(players);
        return this;
    }
    @Override
    public AnimatedBossBar removePlayer(@NonNull Player player) {
        this.playerList.remove(player);
        return this;
    }

    @Override
    public AnimatedBossBar removePlayers(@NonNull Player...players) {
        this.playerList.removeAll(List.of(players));
        return this;
    }
    @Override
    public AnimatedBossBar removePlayers(@NonNull Collection<? extends Player> players) {
        this.playerList.removeAll(players);
        return this;
    }
    @Override
    public AnimatedBossBar addBossBar(@NonNull BossBar bossBar) {
        bossBarList.add(bossBar);
        return this;
    }
    @Override
    public AnimatedBossBar addBossBars(@NonNull BossBar...bossBars) {
        bossBarList.addAll(List.of(bossBars));
        return this;
    }
}
