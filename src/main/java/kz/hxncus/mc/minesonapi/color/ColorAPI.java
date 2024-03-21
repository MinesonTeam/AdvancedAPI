package kz.hxncus.mc.minesonapi.color;

import com.google.common.collect.ImmutableMap;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import kz.hxncus.mc.minesonapi.color.caching.LruCache;
import kz.hxncus.mc.minesonapi.color.pattern.GradientPattern;
import kz.hxncus.mc.minesonapi.color.pattern.Pattern;
import kz.hxncus.mc.minesonapi.color.pattern.RainbowPattern;
import kz.hxncus.mc.minesonapi.color.pattern.SolidPattern;
import kz.hxncus.mc.minesonapi.reflect.ReflectMethod;
import kz.hxncus.mc.minesonapi.reflect.Version;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class ColorAPI {
    private ColorAPI() {

    }
    private static final ReflectMethod METHOD_OF = new ReflectMethod(ChatColor.class, "of", Color.class);

    protected static final List<String> SPECIAL_COLORS = Arrays.asList("&l", "&n", "&o", "&k", "&m");

    private static final LruCache LRU_CACHE = new LruCache(1);

    private static final ImmutableMap<Object, Object> COLORS = ImmutableMap.builder().put(new Color(0), ChatColor.getByChar('0'))
                                                                           .put(new Color(170), ChatColor.getByChar('1'))
                                                                           .put(new Color(43520), ChatColor.getByChar('2'))
                                                                           .put(new Color(43690), ChatColor.getByChar('3'))
                                                                           .put(new Color(11141120), ChatColor.getByChar('4'))
                                                                           .put(new Color(11141290), ChatColor.getByChar('5'))
                                                                           .put(new Color(16755200), ChatColor.getByChar('6'))
                                                                           .put(new Color(11184810), ChatColor.getByChar('7'))
                                                                           .put(new Color(5592405), ChatColor.getByChar('8'))
                                                                           .put(new Color(5592575), ChatColor.getByChar('9'))
                                                                           .put(new Color(5635925), ChatColor.getByChar('a'))
                                                                           .put(new Color(5636095), ChatColor.getByChar('b'))
                                                                           .put(new Color(16733525), ChatColor.getByChar('c'))
                                                                           .put(new Color(16733695), ChatColor.getByChar('d'))
                                                                           .put(new Color(16777045), ChatColor.getByChar('e'))
                                                                           .put(new Color(16777215), ChatColor.getByChar('f')).build();

    private static final List<Pattern> PATTERNS = Arrays.asList(new GradientPattern(), new SolidPattern(), new RainbowPattern());

    @Nonnull
    public static String process(@Nonnull String string) {
        Bukkit.getLogger();
        String result = LRU_CACHE.getResult(string);
        if (result != null)
            return result;
        String input = string;
        for (Pattern pattern : PATTERNS)
            string = pattern.process(string);
        string = ChatColor.translateAlternateColorCodes('&', string);
        LRU_CACHE.put(input, string);
        return string;
    }

    @Nonnull
    public static List<String> process(@Nonnull List<String> strings) {
        strings.replaceAll(ColorAPI::process);
        return strings;
    }

    @Nonnull
    public static String color(@Nonnull String string, @Nonnull Color color) {
        return (Version.supportsHex() ? (String)METHOD_OF.invokeStatic(color) : getClosestColor(color)) + string;
    }

    @Nonnull
    public static String color(@Nonnull String string, @Nonnull Color start, @Nonnull Color end) {
        StringBuilder specialColors = new StringBuilder();
        for (String color : SPECIAL_COLORS) {
            if (string.contains(color)) {
                specialColors.append(color);
                string = string.replace(color, "");
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        ChatColor[] colors = createGradient(start, end, string.length());
        String[] characters = string.split("");
        for (int i = 0; i < string.length(); i++)
            stringBuilder.append(colors[i]).append(specialColors).append(characters[i]);
        return stringBuilder.toString();
    }

    @Nonnull
    public static String rainbow(@Nonnull String string, float saturation) {
        StringBuilder specialColors = new StringBuilder();
        for (String color : SPECIAL_COLORS) {
            if (string.contains(color)) {
                specialColors.append(color);
                string = string.replace(color, "");
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        ChatColor[] colors = createRainbow(string.length(), saturation);
        String[] characters = string.split("");
        for (int i = 0; i < string.length(); i++)
            stringBuilder.append(colors[i]).append(specialColors).append(characters[i]);
        return stringBuilder.toString();
    }

    @Nonnull
    public static ChatColor getColor(@Nonnull String string) {
        return Version.supportsHex() ? (ChatColor)METHOD_OF.invokeStatic(new Color(Integer.parseInt(string, 16))) : getClosestColor(new Color(Integer.parseInt(string, 16)));
    }

    @Nonnull
    public static String stripColorFormatting(@Nonnull String string) {
        return string.replaceAll("&\\w{5,8}(:[0-9A-F]{6})?>", "");
    }

    @Nonnull
    private static ChatColor[] createRainbow(int step, float saturation) {
        ChatColor[] colors = new ChatColor[step];
        double colorStep = 1.0D / step;
        for (int i = 0; i < step; i++) {
            Color color = Color.getHSBColor((float)(colorStep * i), saturation, saturation);
            if (Version.supportsHex()) {
                colors[i] = (ChatColor)METHOD_OF.invokeStatic(color);
            } else {
                colors[i] = getClosestColor(color);
            }
        }
        return colors;
    }

    @Nonnull
    private static ChatColor[] createGradient(@Nonnull Color start, @Nonnull Color end, int step) {
        if (step <= 1)
            return new ChatColor[] { ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE };
        ChatColor[] colors = new ChatColor[step];
        int stepR = Math.abs(start.getRed() - end.getRed()) / (step - 1);
        int stepG = Math.abs(start.getGreen() - end.getGreen()) / (step - 1);
        int stepB = Math.abs(start.getBlue() - end.getBlue()) / (step - 1);
        int[] direction = { (start.getRed() < end.getRed()) ? 1 : -1, (start.getGreen() < end.getGreen()) ? 1 : -1, (start.getBlue() < end.getBlue()) ? 1 : -1 };
        for (int i = 0; i < step; i++) {
            Color color = new Color(start.getRed() + stepR * i * direction[0], start.getGreen() + stepG * i * direction[1], start.getBlue() + stepB * i * direction[2]);
            if (Version.supportsHex()) {
                colors[i] = METHOD_OF.invokeStatic(color);
            } else {
                colors[i] = getClosestColor(color);
            }
        }
        return colors;
    }

    @Nonnull
    private static ChatColor getClosestColor(Color color) {
        Color nearestColor = null;
        double nearestDistance = 2.147483647E9D;
        for (Object colorObject : COLORS.keySet()) {
            Color constantColor = (Color) colorObject;
            double distance = Math.pow((color.getRed() - constantColor.getRed()), 2.0D) + Math.pow((color.getGreen() - constantColor.getGreen()), 2.0D) + Math.pow((color.getBlue() - constantColor.getBlue()), 2.0D);
            if (nearestDistance > distance) {
                nearestColor = constantColor;
                nearestDistance = distance;
            }
        }
        return (ChatColor) COLORS.get(nearestColor);
    }
}

