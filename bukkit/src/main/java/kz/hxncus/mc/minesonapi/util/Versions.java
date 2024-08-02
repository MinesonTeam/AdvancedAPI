package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.util.reflect.ReflectionUtil;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;

@Getter
public enum Versions {
    V1_8_R1(81),
    V1_8_R2(82),
    V1_8_R3(83),
    V1_9_R1(91),
    V1_9_R2(92),
    V1_10_R1(101),
    V1_11_R1(111),
    V1_12_R1(121),
    V1_13_R1(131),
    V1_13_R2(132),
    V1_14_R1(141),
    V1_15_R1(151),
    V1_16_R1(161),
    V1_16_R2(162),
    V1_16_R3(163),
    V1_17_R1(171),
    V1_18_R1(181),
    V1_18_R2(182),
    V1_19_R1(191),
    V1_19_R2(192),
    V1_19_R3(193),
    V1_20_R1(201),
    V1_20_R2(202),
    V1_20_R3(203);

    public static final Versions CURRENT = fromString(ReflectionUtil.getVersion());
    private final int minor;

    public static Versions fromString(String version) {
        if (version == null)
            return null;
        for (Versions value : values()) {
            if (value.name().equalsIgnoreCase(version)) {
                return value;
            }
        }
        return null;
    }

    public static boolean is(int minor) {
        return (CURRENT.getMinor() == minor);
    }

    public static boolean is(@NonNull Versions versions) {
        return (CURRENT == versions);
    }

    public static boolean after(int minor) {
        return (CURRENT.getMinor() > minor);
    }

    public static boolean after(@NonNull Versions versions) {
        return (CURRENT.ordinal() > versions.ordinal());
    }

    public static boolean afterOrEqual(int minor) {
        return (CURRENT.getMinor() >= minor);
    }

    public static boolean afterOrEqual(@NonNull Versions versions) {
        return (CURRENT.ordinal() >= versions.ordinal());
    }

    public static boolean before(int minor) {
        return (CURRENT.getMinor() < minor);
    }

    public static boolean before(@NonNull Versions versions) {
        return (CURRENT.ordinal() < versions.ordinal());
    }

    public static boolean beforeOrEqual(int minor) {
        return (CURRENT.getMinor() <= minor);
    }

    public static boolean beforeOrEqual(@NonNull Versions versions) {
        return (CURRENT.ordinal() <= versions.ordinal());
    }

    public static boolean supportsHex() {
        return afterOrEqual(16);
    }

    public static boolean isPaperServer() {
        if (Bukkit.getServer().getName().equalsIgnoreCase("Paper")) {
            return true;
        }
        try {
            Class.forName("com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isFoliaServer() {
        return Bukkit.getServer().getName().equalsIgnoreCase("Folia");
    }

    Versions(int minor) {
        this.minor = minor;
    }
}
