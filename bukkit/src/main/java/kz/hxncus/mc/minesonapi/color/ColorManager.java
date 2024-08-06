package kz.hxncus.mc.minesonapi.color;

import com.google.common.collect.ImmutableMap;
import kz.hxncus.mc.minesonapi.color.caching.LruCache;
import kz.hxncus.mc.minesonapi.color.pattern.GradientPattern;
import kz.hxncus.mc.minesonapi.color.pattern.Pattern;
import kz.hxncus.mc.minesonapi.color.pattern.RainbowPattern;
import kz.hxncus.mc.minesonapi.color.pattern.SolidPattern;
import kz.hxncus.mc.minesonapi.util.VersionUtil;
import kz.hxncus.mc.minesonapi.util.reflect.ReflectMethod;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
public class ColorManager {
	private final ReflectMethod METHOD_OF = new ReflectMethod(ChatColor.class, "of", Color.class);
	private final List<String> SPECIAL_COLORS = Arrays.asList("&l", "&n", "&o", "&k", "&m");
	private final LruCache LRU_CACHE = new LruCache(1);
	private final ImmutableMap<Object, Object> COLORS = ImmutableMap.builder()
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
	
	private final List<Pattern> PATTERNS = Arrays.asList(new GradientPattern(), new SolidPattern(), new RainbowPattern());
	
	@NonNull
	public List<String> process(@NonNull final List<String> strings) {
		strings.replaceAll(this::process);
		return strings;
	}
	
	@NonNull
	public String process(@NonNull String string) {
		final String result = this.LRU_CACHE.getResult(string);
		if (result == null) {
			final String input = string;
			for (final Pattern pattern : this.PATTERNS) {
				string = pattern.process(string);
			}
			string = ChatColor.translateAlternateColorCodes('&', string);
			this.LRU_CACHE.put(input, string);
			return string;
		}
		return result;
	}
	
	@NonNull
	public String color(@NonNull final String string, @NonNull final Color color) {
		return (VersionUtil.IS_HEX_VERSION ? (String) this.METHOD_OF.invokeStatic(color) : this.getClosestColor(color)) + string;
	}
	
	@NonNull
	private ChatColor getClosestColor(@NonNull final Color color) {
		Color nearestColor = null;
		double nearestDistance = 2.147483647E9D;
		for (final Object colorObject : this.COLORS.keySet()) {
			final Color constantColor = (Color) colorObject;
			final double distance = Math.pow((color.getRed() - constantColor.getRed()), 2.0D) + Math.pow((color.getGreen() - constantColor.getGreen()), 2.0D) + Math.pow((color.getBlue() - constantColor.getBlue()), 2.0D);
			if (nearestDistance > distance) {
				nearestColor = constantColor;
				nearestDistance = distance;
			}
		}
		return (ChatColor) this.COLORS.get(nearestColor);
	}
	
	@NonNull
	public String color(@NonNull String string, @NonNull final Color start, @NonNull final Color end) {
		final StringBuilder specialColors = new StringBuilder();
		for (final String color : this.SPECIAL_COLORS) {
			if (string.contains(color)) {
				specialColors.append(color);
				string = string.replace(color, "");
			}
		}
		final StringBuilder stringBuilder = new StringBuilder();
		final ChatColor[] colors = this.createGradient(start, end, string.length());
		final String[] characters = string.split("");
		for (int i = 0; i < string.length(); i++)
			stringBuilder.append(colors[i])
			             .append(specialColors)
			             .append(characters[i]);
		return stringBuilder.toString();
	}
	
	@NonNull
	private ChatColor[] createGradient(@NonNull final Color start, @NonNull final Color end, final int step) {
		if (step <= 1)
			return new ChatColor[]{ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE};
		final ChatColor[] colors = new ChatColor[step];
		final int stepR = Math.abs(start.getRed() - end.getRed()) / (step - 1);
		final int stepG = Math.abs(start.getGreen() - end.getGreen()) / (step - 1);
		final int stepB = Math.abs(start.getBlue() - end.getBlue()) / (step - 1);
		final int[] direction = {(start.getRed() < end.getRed()) ? 1 : -1, (start.getGreen() < end.getGreen()) ? 1 : -1, (start.getBlue() < end.getBlue()) ? 1 : -1};
		for (int i = 0; i < step; i++) {
			final Color color = new Color(start.getRed() + stepR * i * direction[0], start.getGreen() + stepG * i * direction[1], start.getBlue() + stepB * i * direction[2]);
			if (VersionUtil.IS_HEX_VERSION) {
				colors[i] = this.METHOD_OF.invokeStatic(color);
			} else {
				colors[i] = this.getClosestColor(color);
			}
		}
		return colors;
	}
	
	@NonNull
	public String rainbow(@NonNull String string, final float saturation) {
		final StringBuilder specialColors = new StringBuilder();
		for (final String color : this.SPECIAL_COLORS) {
			if (string.contains(color)) {
				specialColors.append(color);
				string = string.replace(color, "");
			}
		}
		final StringBuilder stringBuilder = new StringBuilder();
		final ChatColor[] colors = this.createRainbow(string.length(), saturation);
		final String[] characters = string.split("");
		for (int i = 0; i < string.length(); i++)
			stringBuilder.append(colors[i])
			             .append(specialColors)
			             .append(characters[i]);
		return stringBuilder.toString();
	}
	
	@NonNull
	private ChatColor[] createRainbow(final int step, final float saturation) {
		final ChatColor[] colors = new ChatColor[step];
		final double colorStep = 1.0D / step;
		for (int i = 0; i < step; i++) {
			final Color color = Color.getHSBColor((float) (colorStep * i), saturation, saturation);
			if (VersionUtil.IS_HEX_VERSION) {
				colors[i] = this.METHOD_OF.invokeStatic(color);
			} else {
				colors[i] = this.getClosestColor(color);
			}
		}
		return colors;
	}
	
	@NonNull
	public ChatColor getColor(@NonNull final String string) {
		return VersionUtil.IS_HEX_VERSION ? (ChatColor) this.METHOD_OF.invokeStatic(new Color(Integer.parseInt(string, 16))) : this.getClosestColor(new Color(Integer.parseInt(string, 16)));
	}
	
	@NonNull
	public String stripColorFormatting(@NonNull final String string) {
		return string.replaceAll("&\\w{5,8}(:[0-9A-F]{6})?>", "");
	}
}

