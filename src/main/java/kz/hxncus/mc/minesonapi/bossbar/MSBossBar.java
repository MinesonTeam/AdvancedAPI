package kz.hxncus.mc.minesonapi.bossbar;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;

import java.util.*;

@NoArgsConstructor
public class MSBossBar {
    private String name;
    private float progress;
    private BossBar.Color color;
    private BossBar.Overlay overlay;
    static float MIN_PROGRESS = 0f;
    static float MAX_PROGRESS = 1f;
    public MSBossBar(@NonNull final String name, final float progress, @NonNull final BossBar.Color color, @NonNull final BossBar.Overlay overlay) {
        this.name = name;
        this.progress = checkProgress(progress);
        this.color = color;
        this.overlay = overlay;
    }
    @NonNull
    public BossBar build() {
        return BossBar.bossBar(Component.text(this.name), this.progress, this.color, this.overlay);
    }
    public @NonNull String name() {
        return this.name;
    }

    public @NonNull MSBossBar name(@NonNull String name) {
        this.name = name;
        return this;
    }

    public float progress() {
        return this.progress;
    }

    public @NonNull MSBossBar progress(float progress) {
        this.progress = checkProgress(progress);
        return this;
    }


    public @NonNull BossBar.Color color() {
        return this.color;
    }


    public @NonNull MSBossBar color(@NonNull BossBar.Color color) {
        this.color = color;
        return this;
    }


    public @NonNull BossBar.Overlay overlay() {
        return this.overlay;
    }


    public @NonNull MSBossBar overlay(@NonNull BossBar.Overlay overlay) {
        this.overlay = overlay;
        return this;
    }

    /**
     * MIN_PROGRESS >= progress >= MAX_PROGRESS
     * @param progress bossbar progress
     * @return progress in range
     */
    private float checkProgress(float progress) {
        return Math.max(Math.min(progress, MAX_PROGRESS), MIN_PROGRESS);
    }
}








