package kz.hxncus.mc.minesonapi.color.pattern;

import kz.hxncus.mc.minesonapi.color.MSColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RainbowPattern implements kz.hxncus.mc.minesonapi.color.pattern.Pattern {
    private static final java.util.regex.Pattern PATTERN = Pattern.compile("<RAINBOW(\\d{1,3})>(.*?)</RAINBOW>");

    public String process(String string) {
        Matcher matcher = PATTERN.matcher(string);
        while (matcher.find()) {
            String saturation = matcher.group(1);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), MSColor.rainbow(content, Float.parseFloat(saturation)));
        }
        return string;
    }
}