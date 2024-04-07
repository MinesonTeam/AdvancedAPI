package kz.hxncus.mc.minesonapi.bossbar.animation;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Nullable;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public enum AnimationType {
    PROGRESSIVE(ProgressiveBossBar.class), STATIC(StaticBossBar.class);
    Class<? extends IBossBar> bossBarClass;
    AnimationType(Class<? extends IBossBar> bossBarClass) {
        this.bossBarClass = bossBarClass;
    }
    public Class<? extends IBossBar> getBossBarClass() {
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
