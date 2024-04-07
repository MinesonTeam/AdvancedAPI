package kz.hxncus.mc.minesonapi.bossbar.animation;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Nullable;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public enum AnimationType {
    PROGRESSIVE(ProgressiveBossBar.class), STATIC(StaticBossBar.class);
    Class<? extends BossBar> bossBarClass;
    AnimationType(Class<? extends BossBar> bossBarClass) {
        this.bossBarClass = bossBarClass;
    }
    public Class<? extends BossBar> getBossBarClass() {
        return bossBarClass;
    }

    @Nullable
    public static AnimationType fromClass(Class<?> bossBarClass) {
        for (AnimationType type : values()) {
            if (type.getBossBarClass() == bossBarClass) {
                return type;
            }
        }
        return null;
    }
}
