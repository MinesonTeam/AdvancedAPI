package kz.hxncus.mc.minesonapi.utility;

import com.google.common.collect.ImmutableMap;
import kz.hxncus.mc.minesonapi.api.color.pattern.Pattern;
import kz.hxncus.mc.minesonapi.caching.LruCache;
import kz.hxncus.mc.minesonapi.color.pattern.GradientPattern;
import kz.hxncus.mc.minesonapi.color.pattern.RainbowPattern;
import kz.hxncus.mc.minesonapi.color.pattern.SolidPattern;
import kz.hxncus.mc.minesonapi.utility.reflect.ReflectMethod;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Class Color manager.
 *
 * @author Hxncus
 * @since 1.0.
 */
@ToString
@UtilityClass
@EqualsAndHashCode
public class ColorUtil {
	/**
	 * The constant BUKKIT_COLOR_CHAR.
	 */
	public final char BUKKIT_COLOR_CHAR = '&';
	private final java.util.regex.Pattern PATTERN = java.util.regex.Pattern.compile("&\\w{5,8}(:[0-9A-F]{6})?>");
	private final ReflectMethod methodOf = new ReflectMethod(ChatColor.class, "of", Color.class);
	private final List<String> specialColors = Arrays.asList("&l", "&n", "&o", "&k", "&m");
	private final LruCache lruCache = new LruCache(1);
	private final ImmutableMap<Object, Object> colors = ImmutableMap.builder()
	                                                                .put(new Color(0), ChatColor.getByChar('0'))
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
	                                                                .put(new Color(16777215), ChatColor.getByChar('f'))
	                                                                .build();
	
	private final List<Pattern> patterns = Arrays.asList(new GradientPattern(), new SolidPattern(), new RainbowPattern());
	
	/**
	 * Process list.
	 *
	 * @param strings the strings
	 * @return the list
	 */
	@NonNull
	public List<String> process(@NonNull final List<String> strings) {
		strings.replaceAll(ColorUtil::process);
		return strings;
	}
	
	/**
	 * Process message.
	 *
	 * @param message the message
	 * @return the message
	 */
	@NonNull
	public String process(@NonNull final String message) {
		String str = message;
		final String result = ColorUtil.lruCache.getResult(str);
		if (result == null) {
			final String input = str;
			for (final Pattern pattern : ColorUtil.patterns) {
				str = pattern.process(str);
			}
			str = ChatColor.translateAlternateColorCodes(BUKKIT_COLOR_CHAR, str);
			ColorUtil.lruCache.put(input, str);
			return str;
		}
		return result;
	}
	
	/**
	 * Color message.
	 *
	 * @param message the message
	 * @param color   the color
	 * @return the message
	 */
	@NonNull
	public String color(@NonNull final String message, @NonNull final Color color) {
		return (VersionUtil.IS_HEX_VERSION ? (String) ColorUtil.methodOf.invokeStatic(color) : ColorUtil.getClosestColor(color)) + message;
	}
	
	@NonNull
	private ChatColor getClosestColor(@NonNull final Color color) {
		Color nearestColor = null;
		double nearestDistance = 2.147483647E9D;
		for (final Object colorObject : ColorUtil.colors.keySet()) {
			final Color constantColor = (Color) colorObject;
			final double distance = StrictMath.pow((color.getRed() - constantColor.getRed()), 2.0D) + StrictMath.pow((color.getGreen() - constantColor.getGreen()), 2.0D) + StrictMath.pow((color.getBlue() - constantColor.getBlue()), 2.0D);
			if (nearestDistance > distance) {
				nearestColor = constantColor;
				nearestDistance = distance;
			}
		}
		return (ChatColor) ColorUtil.colors.get(nearestColor);
	}
	
	/**
	 * Color message.
	 *
	 * @param message the message
	 * @param start   the start
	 * @param end     the end
	 * @return the message
	 */
	@NonNull
	public String color(@NonNull final String message, @NonNull final Color start, @NonNull final Color end) {
		String str = message;
		final StringBuilder colorsBuilder = new StringBuilder(ColorUtil.specialColors.size());
		for (final String color : ColorUtil.specialColors) {
			if (str.contains(color)) {
				colorsBuilder.append(color);
				str = str.replace(color, "");
			}
		}
		final int length = str.length();
		final StringBuilder stringBuilder = new StringBuilder(length * 3);
		final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("");
		final String[] characters = pattern.split(str);
		final ChatColor[] gradient = ColorUtil.createGradient(start, end, length);
		for (int i = 0; i < length; i++)
			stringBuilder.append(gradient[i])
			             .append(colorsBuilder)
			             .append(characters[i]);
		return stringBuilder.toString();
	}
	
	@NonNull
	private ChatColor[] createGradient(@NonNull final Color start, @NonNull final Color end, final int step) {
		if (step <= 1)
			return new ChatColor[]{ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE};
		final ChatColor[] chatColors = new ChatColor[step];
		final int stepR = Math.abs(start.getRed() - end.getRed()) / (step - 1);
		final int stepG = Math.abs(start.getGreen() - end.getGreen()) / (step - 1);
		final int stepB = Math.abs(start.getBlue() - end.getBlue()) / (step - 1);
		final int[] direction = {(start.getRed() < end.getRed()) ? 1 : -1, (start.getGreen() < end.getGreen()) ? 1 : -1, (start.getBlue() < end.getBlue()) ? 1 : -1};
		for (int i = 0; i < step; i++) {
			final Color color = new Color(start.getRed() + stepR * i * direction[0], start.getGreen() + stepG * i * direction[1], start.getBlue() + stepB * i * direction[2]);
			if (VersionUtil.IS_HEX_VERSION) {
				chatColors[i] = ColorUtil.methodOf.invokeStatic(color);
			} else {
				chatColors[i] = ColorUtil.getClosestColor(color);
			}
		}
		return chatColors;
	}
	
	/**
	 * Rainbow message.
	 *
	 * @param message     the message
	 * @param saturation the saturation
	 * @return the message
	 */
	@NonNull
	public String rainbow(@NonNull final String message, final float saturation) {
		String str = message;
		final StringBuilder colorsBuilder = new StringBuilder(ColorUtil.specialColors.size());
		for (final String color : ColorUtil.specialColors) {
			if (str.contains(color)) {
				colorsBuilder.append(color);
				str = str.replace(color, "");
			}
		}
		final int length = str.length();
		final StringBuilder stringBuilder = new StringBuilder(length * 3);
		final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("");
		final String[] characters = pattern.split(str);
		final ChatColor[] rainbow = ColorUtil.createRainbow(length, saturation);
		for (int i = 0; i < length; i++)
			stringBuilder.append(rainbow[i])
			             .append(colorsBuilder)
			             .append(characters[i]);
		return stringBuilder.toString();
	}
	
	@NonNull
	private ChatColor[] createRainbow(final int step, final float saturation) {
		final ChatColor[] chatColors = new ChatColor[step];
		final double colorStep = 1.0D / step;
		for (int i = 0; i < step; i++) {
			final Color color = Color.getHSBColor((float) (colorStep * i), saturation, saturation);
			if (VersionUtil.IS_HEX_VERSION) {
				chatColors[i] = ColorUtil.methodOf.invokeStatic(color);
			} else {
				chatColors[i] = ColorUtil.getClosestColor(color);
			}
		}
		return chatColors;
	}
	
	/**
	 * Gets color.
	 *
	 * @param message the message
	 * @return the color
	 */
	@NonNull
	public ChatColor getColor(@NonNull final String message) {
		return VersionUtil.IS_HEX_VERSION ? (ChatColor) ColorUtil.methodOf.invokeStatic(new Color(Integer.parseInt(message, 16))) : ColorUtil.getClosestColor(new Color(Integer.parseInt(message, 16)));
	}
	
	/**
	 * Strip color formatting message.
	 *
	 * @param message the message
	 * @return the message
	 */
	@NonNull
	public String stripColorFormatting(@NonNull final CharSequence message) {
		return PATTERN.matcher(message).replaceAll("");
	}
}

