package kz.hxncus.mc.minesonapi.reflect;

import lombok.NonNull;

import javax.annotation.Nullable;

public enum Version {
    V1_8_R1(8),
    V1_8_R2(8),
    V1_8_R3(8),
    V1_9_R1(9),
    V1_9_R2(9),
    V1_10_R1(10),
    V1_11_R1(11),
    V1_12_R1(12),
    V1_13_R1(13),
    V1_13_R2(13),
    V1_14_R1(14),
    V1_15_R1(15),
    V1_16_R1(16),
    V1_16_R2(16),
    V1_16_R3(16),
    V1_17_R1(17),
    V1_18_R1(18),
    V1_18_R2(18),
    V1_19_R1(19),
    V1_19_R2(19),
    V1_19_R3(19),
    V1_20_R1(20),
    V1_20_R2(20),
    V1_20_R3(20);

    public static final Version CURRENT;

    private final int minor;

    static {
        CURRENT = fromString(ReflectionUtil.getVersion());
    }

    @Nullable
    public static Version fromString(String version) {
        if (version == null)
            return null;
        for (Version value : values()) {
            if (value.name().equalsIgnoreCase(version))
                return value;
        }
        return null;
    }

    public static boolean is(int minor) {
        return (CURRENT.getMinor() == minor);
    }

    public static boolean is(@NonNull Version version) {
        return (CURRENT == version);
    }

    public static boolean after(int minor) {
        return (CURRENT.getMinor() > minor);
    }

    public static boolean after(@NonNull Version version) {
        return (CURRENT.ordinal() > version.ordinal());
    }

    public static boolean afterOrEqual(int minor) {
        return (CURRENT.getMinor() >= minor);
    }

    public static boolean afterOrEqual(@NonNull Version version) {
        return (CURRENT.ordinal() >= version.ordinal());
    }

    public static boolean before(int minor) {
        return (CURRENT.getMinor() < minor);
    }

    public static boolean before(@NonNull Version version) {
        return (CURRENT.ordinal() < version.ordinal());
    }

    public static boolean beforeOrEqual(int minor) {
        return (CURRENT.getMinor() <= minor);
    }

    public static boolean beforeOrEqual(@NonNull Version version) {
        return (CURRENT.ordinal() <= version.ordinal());
    }

    public static boolean supportsHex() {
        return afterOrEqual(16);
    }

    Version(int minor) {
        this.minor = minor;
    }

    public int getMinor() {
        return this.minor;
    }
}
