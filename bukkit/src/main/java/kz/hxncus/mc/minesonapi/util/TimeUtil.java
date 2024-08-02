package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class TimeUtil {
    public long ticksToMillis(long ticks) {
        return ticks * 50L;
    }

    public long fpsToMillis(double fps) {
        return (long) (1000L / fps);
    }

    public Long toMillis(String input) {
        String lowerCase = input.toLowerCase(Locale.ENGLISH);
        if (lowerCase.endsWith("t")) {
            return ticksToMillis(Long.parseLong(input.substring(0, input.length() - 1)));
        } else if (lowerCase.endsWith("ms")) {
            return Long.parseLong(input.substring(0, input.length() - 2));
        } else if (lowerCase.endsWith("fps")) {
            return fpsToMillis(Double.parseDouble(input.substring(0, input.length() - 3)));
        } else {
            return Long.parseLong(lowerCase);
        }
    }
}
