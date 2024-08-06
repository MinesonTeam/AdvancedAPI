package kz.hxncus.mc.minesonapi.color.pattern;

import kz.hxncus.mc.minesonapi.MinesonAPI;

import java.awt.*;
import java.util.regex.Matcher;

public class GradientPattern implements Pattern {
	private static final java.util.regex.Pattern PATTERN = java.util.regex.Pattern.compile("[<{]#([A-Fa-f0-9]{6})[}>](((?![<{]#[A-Fa-f0-9]{6}[}>]).)*)[<{]/#([A-Fa-f0-9]{6})[}>]");
	
	public String process(String string) {
		final Matcher matcher = PATTERN.matcher(string);
		while (matcher.find()) {
			final String start = matcher.group(1);
			final String content = matcher.group(2);
			final String end = matcher.group(4);
			string = string.replace(
					matcher
							.group(),
					MinesonAPI.getInstance()
					          .getColorManager()
					          .color(content, new Color(
							          
							          Integer.parseInt(start, 16)), new Color(
							          Integer.parseInt(end, 16)))
			);
		}
		return string;
	}
}
