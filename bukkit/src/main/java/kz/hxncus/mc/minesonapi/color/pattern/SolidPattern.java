package kz.hxncus.mc.minesonapi.color.pattern;

import kz.hxncus.mc.minesonapi.MinesonAPI;

import java.util.regex.Matcher;

public class SolidPattern implements kz.hxncus.mc.minesonapi.color.pattern.Pattern {
    public static final java.util.regex.Pattern PATTERN = java.util.regex.Pattern.compile("[<{]#([A-Fa-f0-9]{6})[}>]|&?#([A-Fa-f0-9]{6})");

    public String process(String string) {
        Matcher matcher = PATTERN.matcher(string);
        while (matcher.find()) {
            String color = matcher.group(1);
            if (color == null)
                color = matcher.group(2);
            string = string.replace(matcher.group(), String.valueOf(MinesonAPI.get().getColorManager().getColor(color)));
        }
        return string;
    }
}
