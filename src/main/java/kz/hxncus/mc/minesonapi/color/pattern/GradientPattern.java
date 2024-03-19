package kz.hxncus.mc.minesonapi.color.pattern;

import kz.hxncus.mc.minesonapi.color.ColorAPI;

import java.awt.*;
import java.util.regex.Matcher;

public class GradientPattern implements Pattern {
    private static final java.util.regex.Pattern PATTERN = java.util.regex.Pattern.compile("[<{]#([A-Fa-f0-9]{6})[}>](((?![<{]#[A-Fa-f0-9]{6}[}>]).)*)[<{]/#([A-Fa-f0-9]{6})[}>]");

    public String process(String string) {
        Matcher matcher = PATTERN.matcher(string);
        while (matcher.find()) {
            String start = matcher.group(1);
            String content = matcher.group(2);
            String end = matcher.group(4);
            string = string.replace(matcher
                            .group(),
                    ColorAPI.color(content, new Color(

                            Integer.parseInt(start, 16)), new Color(
                            Integer.parseInt(end, 16))));
        }
        return string;
    }
}
