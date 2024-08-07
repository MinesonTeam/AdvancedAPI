package kz.hxncus.mc.minesonapi.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * The type Time util.
 *
 * @author Hxncus
 * @since 1.0.0
 */
@UtilityClass
public class TimeUtil {
	private final Pattern PATTERN = Pattern.compile("(?<=\\\\D)(?=\\\\d)|(?<=\\\\d)(?=\\\\D)");
	/**
	 * The Milliseconds in second.
	 */
	public final long MILLISECONDS_IN_SECOND = 1000L;
	/**
	 * The Seconds in minute.
	 */
	public final int SECONDS_IN_MINUTE = 60;
	/**
	 * The Minutes in hour.
	 */
	public final short MINUTES_IN_HOUR = 60;
	/**
	 * The Milliseconds in tick.
	 */
	public final long MILLISECONDS_IN_TICK = 50L;
	
	/**
	 * Hours to millis long.
	 *
	 * @param hours the hours
	 * @return the long
	 */
	public long hoursToMillis(final long hours) {
		return minutesToMillis(hours * MINUTES_IN_HOUR);
	}
	
	/**
	 * Minutes to millis long.
	 *
	 * @param minutes the minutes
	 * @return the long
	 */
	public long minutesToMillis(final long minutes) {
		return secondsToMillis(minutes * SECONDS_IN_MINUTE);
	}
	
	/**
	 * Seconds to millis long.
	 *
	 * @param seconds the seconds
	 * @return the long
	 */
	public long secondsToMillis(final long seconds) {
		return seconds * MILLISECONDS_IN_SECOND;
	}
	
	/**
	 * String to millis long.
	 *
	 * @param input the input
	 * @return the long
	 */
	public Long stringToMillis(@NonNull final CharSequence input) {
		final String[] splitted = PATTERN.split(input);
		final long parsedLong = Long.parseLong(splitted[0]);
		if (splitted.length < 2) {
			return parsedLong;
		}
		final String lowerCase = splitted[1].toLowerCase(Locale.ENGLISH);
		return switch (lowerCase) {
			case "tick", "ticks", "t" -> ticksToMillis(parsedLong);
			case "seconds", "second", "s" -> secondsToMillis(parsedLong);
			case "minutes", "minute", "m" -> minutesToMillis(parsedLong);
			case "fps", "framepersecond" -> fpsToMillis(Double.parseDouble(splitted[0]));
			default -> parsedLong;
		};
	}
	
	/**
	 * Ticks to millis long.
	 *
	 * @param ticks the ticks
	 * @return the long
	 */
	public long ticksToMillis(final long ticks) {
		return ticks * MILLISECONDS_IN_TICK;
	}
	
	/**
	 * Fps to millis long.
	 *
	 * @param fps the fps
	 * @return the long
	 */
	public long fpsToMillis(final double fps) {
		return (long) (MILLISECONDS_IN_SECOND / fps);
	}
}
