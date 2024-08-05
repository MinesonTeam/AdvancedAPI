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
        long parsedLong = Long.parseLong(splitted[0]);
        if (splitted.length < 2) {
            return parsedLong;
        }
        String lowerCase = splitted[1].toLowerCase(Locale.ENGLISH);
        switch (lowerCase) {
            case "tick":
            case "ticks":
            case "t":
                return ticksToMillis(parsedLong);
            case "seconds":
            case "second":
            case "s":
                return secondsToMillis(parsedLong);
            case "minutes":
            case "minute":
            case "m":
                return minutesToMillis(parsedLong);
            case "fps":
            case "framepersecond":
                return fpsToMillis(Double.parseDouble(splitted[0]));
            default:
                return parsedLong;
        }
    }
}
