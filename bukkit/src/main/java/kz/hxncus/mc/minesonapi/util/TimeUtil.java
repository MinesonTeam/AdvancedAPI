package kz.hxncus.mc.minesonapi.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class TimeUtil {
	public long hoursToMillis(final long hours) {
		return minutesToMillis(hours * 60);
	}
	
	public long minutesToMillis(final long minutes) {
		return secondsToMillis(minutes * 60);
	}
	
	public long secondsToMillis(final long seconds) {
		return seconds * 1000L;
	}
	
	public Long stringToMillis(@NonNull final String input) {
		final String[] splitted = input.split("(?<=\\\\D)(?=\\\\d)|(?<=\\\\d)(?=\\\\D)");
		final long parsedLong = Long.parseLong(splitted[0]);
		if (splitted.length < 2) {
			return parsedLong;
		}
		final String lowerCase = splitted[1].toLowerCase(Locale.ENGLISH);
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
	
	public long ticksToMillis(final long ticks) {
		return ticks * 50L;
	}
	
	public long fpsToMillis(final double fps) {
		return (long) (1000L / fps);
	}
}
