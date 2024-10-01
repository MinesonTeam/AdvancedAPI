package kz.hxncus.mc.minesonapi.color.pattern;

import kz.hxncus.mc.minesonapi.utility.ColorUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class a Rainbow pattern.
 *
 * @author Hxncus
 * @since 1.0.0
 */
public class RainbowPattern implements kz.hxncus.mc.minesonapi.api.color.pattern.Pattern {
	private static final java.util.regex.Pattern PATTERN = Pattern.compile("<RAINBOW(\\d{1,3})>(.*?)</RAINBOW>");
	
	public String process(final String message) {
		String result = message;
		final Matcher matcher = PATTERN.matcher(result);
		while (true) {
			final boolean ifNotFound = !matcher.find();
			if (ifNotFound) {
				break;
			}
			final String saturation = matcher.group(1);
			final String content = matcher.group(2);
			result = result.replace(matcher.group(), ColorUtil.rainbow(content, Float.parseFloat(saturation)));
		}
		return result;
	}
}