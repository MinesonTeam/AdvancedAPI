package kz.hxncus.mc.advancedapi.color.pattern;

import kz.hxncus.mc.advancedapi.api.color.pattern.Pattern;
import kz.hxncus.mc.advancedapi.utility.ColorUtil;

import java.awt.*;
import java.util.regex.Matcher;

/**
 * Class Gradient pattern.
 *
 * @author Hxncus
 * @since 1.0.0
 */
public class GradientPattern implements Pattern {
	private static final java.util.regex.Pattern PATTERN = java.util.regex.Pattern.compile("[<{]#([A-Fa-f0-9]{6})[}>](((?![<{]#[A-Fa-f0-9]{6}[}>]).)*)[<{]/#([A-Fa-f0-9]{6})[}>]");
	
	public String process(final String message) {
		String result = message;
		final Matcher matcher = PATTERN.matcher(result);
		while (true) {
			final boolean ifNotFound = !matcher.find();
			if (ifNotFound) {
				break;
			}
			final String start = matcher.group(1);
			final String content = matcher.group(2);
			final String end = matcher.group(4);
			result = result.replace(matcher.group(), ColorUtil.color(content,
                                                           new Color(Integer.parseInt(start, 16)),
                                                           new Color(Integer.parseInt(end, 16))));
		}
		return result;
	}
	
	public static String test(final String message) {
		String result = message;
		final Matcher matcher = PATTERN.matcher(result);
		while (true) {
			final boolean ifNotFound = !matcher.find();
			if (ifNotFound) {
				break;
			}
			final String start = matcher.group(1);
			final String content = matcher.group(2);
			final String end = matcher.group(4);
			result = result.replace(matcher.group(), ColorUtil.color(content,
                                                           new Color(Integer.parseInt(start, 16)),
                                                           new Color(Integer.parseInt(end, 16))));
		}
		return result;
	}
}
