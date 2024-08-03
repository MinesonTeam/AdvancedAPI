package kz.hxncus.mc.minesonapi.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class TimeUtil {
    public long hoursToMillis(long hours) {
        return minutesToMillis(hours * 60);
    }

    public long minutesToMillis(long minutes) {
        return secondsToMillis(minutes * 60);
    }

    public long secondsToMillis(long seconds) {
        return seconds * 1000L;
    }

    public long ticksToMillis(long ticks) {
        return ticks * 50L;
    }

    public long fpsToMillis(double fps) {
        return (long) (1000L / fps);
    }

    public Long stringToMillis(@NonNull String input) {
        String[] splitted = input.split("(?<=\\\\D)(?=\\\\d)|(?<=\\\\d)(?=\\\\D)");
        long l = Long.parseLong(splitted[0]);
        if (splitted.length < 2) {
            return l;
        }
        String lowerCase = splitted[1].toLowerCase(Locale.ENGLISH);
        return switch (lowerCase) {
            case "t", "tick", "ticks" -> ticksToMillis(l);
            case "s", "second", "seconds" -> secondsToMillis(l);
            case "m", "minute", "minutes" -> minutesToMillis(l);
            case "fps", "framepersecond" -> fpsToMillis(Double.parseDouble(splitted[0]));
            default -> l;
        };
    }
}
