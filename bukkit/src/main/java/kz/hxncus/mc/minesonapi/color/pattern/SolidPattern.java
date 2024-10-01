package kz.hxncus.mc.minesonapi.color.pattern;

import kz.hxncus.mc.minesonapi.api.color.pattern.Pattern;
import kz.hxncus.mc.minesonapi.utility.ColorUtil;

import java.util.regex.Matcher;

/**
 * Class Solid pattern.
 *
 * @author Hxncus
 * @since 1.0.0
 */
public class SolidPattern implements Pattern {
	/**
	 * The constant PATTERN.
	 */
	public static final java.util.regex.Pattern PATTERN = java.util.regex.Pattern.compile("[<{]#([A-Fa-f0-9]{6})[}>]|&?#([A-Fa-f0-9]{6})");
	
	public String process(final String message) {
		String result = message;
		final Matcher matcher = PATTERN.matcher(result);
		while (true) {
			final boolean ifNotFound = !matcher.find();
			if (ifNotFound) {
				break;
			}
			String color = matcher.group(1);
			if (color == null)
				color = matcher.group(2);
			result = result.replace(matcher.group(), String.valueOf(ColorUtil.getColor(color)));
		}
		return result;
	}
}
