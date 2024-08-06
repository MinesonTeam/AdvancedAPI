package kz.hxncus.mc.minesonapi.color.pattern;

import kz.hxncus.mc.minesonapi.MinesonAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RainbowPattern implements kz.hxncus.mc.minesonapi.color.pattern.Pattern {
	private static final java.util.regex.Pattern PATTERN = Pattern.compile("<RAINBOW(\\d{1,3})>(.*?)</RAINBOW>");
	
	public String process(String string) {
		final Matcher matcher = PATTERN.matcher(string);
		while (matcher.find()) {
			final String saturation = matcher.group(1);
			final String content = matcher.group(2);
			string = string.replace(matcher.group(), MinesonAPI.getInstance()
			                                                   .getColorManager()
			                                                   .rainbow(content, Float.parseFloat(saturation)));
		}
		return string;
	}
}