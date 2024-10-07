package kz.hxncus.mc.minesonapi.utility;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Version util.
 * @author Hxncus
 * @since 1.0.0
 */
@UtilityClass
public class VersionUtil {
	/**
	 * The Nms version by int.
	 */
	public final Map<Integer, String> NMS_VERSION_BY_INT = new HashMap<>(64);
	/**
	 * The Current version.
	 */
	public final int CURRENT_VERSION = getCurrentVersion();
	/**
	 * The Nms version.
	 */
	public final String NMS_VERSION = NMS_VERSION_BY_INT.get(CURRENT_VERSION);
	/**
	 * The Is pdc version.
	 */
	public final boolean IS_PDC_VERSION = CURRENT_VERSION >= 1140;
	/**
	 * The Is hex version.
	 */
	public final boolean IS_HEX_VERSION = CURRENT_VERSION >= 1160;
	/**
	 * The Is target block version.
	 */
	public final boolean IS_TARGET_BLOCK_VERSION = CURRENT_VERSION >= 1140;
	/**
	 * The Is namespaced key version.
	 */
	public final boolean IS_NAMESPACED_KEY_VERSION = CURRENT_VERSION >= 1120;
	/**
	 * The Is spawn egg meta-version.
	 */
	public final boolean IS_SPAWN_EGG_META_VERSION = CURRENT_VERSION >= 1110;
	/**
	 * The Is potion color version.
	 */
	public final boolean IS_POTION_COLOR_VERSION = CURRENT_VERSION >= 1110;
	/**
	 * The Is potion data version.
	 */
	public final boolean IS_POTION_DATA_VERSION = CURRENT_VERSION >= 190;
	/**
	 * The Sign.
	 */
	public final Material SIGN = getSign();
	
	static {
		NMS_VERSION_BY_INT.put(180, "1_8_R1");
		NMS_VERSION_BY_INT.put(183, "1_8_R2");
		NMS_VERSION_BY_INT.put(184, "1_8_R3");
		NMS_VERSION_BY_INT.put(185, "1_8_R3");
		NMS_VERSION_BY_INT.put(186, "1_8_R3");
		NMS_VERSION_BY_INT.put(187, "1_8_R3");
		NMS_VERSION_BY_INT.put(188, "1_8_R3");
		
		NMS_VERSION_BY_INT.put(190, "1_9_R1");
		NMS_VERSION_BY_INT.put(192, "1_9_R1");
		NMS_VERSION_BY_INT.put(194, "1_9_R2");
		
		NMS_VERSION_BY_INT.put(1100, "1_10_R1");
		NMS_VERSION_BY_INT.put(1102, "1_10_R1");
		
		NMS_VERSION_BY_INT.put(1110, "1_11_R1");
		NMS_VERSION_BY_INT.put(1111, "1_11_R1");
		NMS_VERSION_BY_INT.put(1112, "1_11_R1");
		
		NMS_VERSION_BY_INT.put(1120, "1_12_R1");
		NMS_VERSION_BY_INT.put(1121, "1_12_R1");
		NMS_VERSION_BY_INT.put(1122, "1_12_R1");
		
		NMS_VERSION_BY_INT.put(1130, "1_13_R1");
		NMS_VERSION_BY_INT.put(1131, "1_13_R2");
		NMS_VERSION_BY_INT.put(1132, "1_13_R2");
		
		NMS_VERSION_BY_INT.put(1140, "1_14_R1");
		NMS_VERSION_BY_INT.put(1141, "1_14_R1");
		NMS_VERSION_BY_INT.put(1142, "1_14_R1");
		NMS_VERSION_BY_INT.put(1143, "1_14_R1");
		NMS_VERSION_BY_INT.put(1144, "1_14_R1");
		
		NMS_VERSION_BY_INT.put(1150, "1_15_R1");
		NMS_VERSION_BY_INT.put(1151, "1_15_R1");
		NMS_VERSION_BY_INT.put(1152, "1_15_R1");
		
		
		NMS_VERSION_BY_INT.put(1160, "1_16_R1");
		NMS_VERSION_BY_INT.put(1161, "1_16_R1");
		NMS_VERSION_BY_INT.put(1162, "1_16_R2");
		NMS_VERSION_BY_INT.put(1163, "1_16_R2");
		NMS_VERSION_BY_INT.put(1164, "1_16_R3");
		NMS_VERSION_BY_INT.put(1165, "1_16_R3");
		
		NMS_VERSION_BY_INT.put(1170, "1_17_R1");
		NMS_VERSION_BY_INT.put(1171, "1_17_R1");
		
		NMS_VERSION_BY_INT.put(1180, "1_18_R1");
		NMS_VERSION_BY_INT.put(1181, "1_18_R1");
		NMS_VERSION_BY_INT.put(1182, "1_18_R2");
		
		NMS_VERSION_BY_INT.put(1190, "1_19_R1");
		NMS_VERSION_BY_INT.put(1191, "1_19_R1");
		NMS_VERSION_BY_INT.put(1192, "1_19_R1");
		NMS_VERSION_BY_INT.put(1193, "1_19_R2");
		NMS_VERSION_BY_INT.put(1194, "1_19_R3");
		
		NMS_VERSION_BY_INT.put(1200, "1_20_R1");
		NMS_VERSION_BY_INT.put(1201, "1_20_R1");
		NMS_VERSION_BY_INT.put(1202, "1_20_R2");
		NMS_VERSION_BY_INT.put(1203, "1_20_R3");
		NMS_VERSION_BY_INT.put(1204, "1_20_R3");
		NMS_VERSION_BY_INT.put(1205, "1_20_R3");
		NMS_VERSION_BY_INT.put(1206, "1_20_R4");
		
		NMS_VERSION_BY_INT.put(1210, "1_21_R1");
	}
	
	/**
	 * Is equal boolean.
	 *
	 * @param minor the minor
	 * @return the boolean
	 */
	public boolean isEqual(final int minor) {
		return CURRENT_VERSION == minor;
	}
	
	/**
	 * Is after boolean.
	 *
	 * @param minor the minor
	 * @return the boolean
	 */
	public boolean isAfter(final int minor) {
		return CURRENT_VERSION > minor;
	}
	
	/**
	 * Is after or equal boolean.
	 *
	 * @param minor the minor
	 * @return the boolean
	 */
	public boolean isAfterOrEqual(final int minor) {
		return CURRENT_VERSION >= minor;
	}
	
	/**
	 * Is before boolean.
	 *
	 * @param minor the minor
	 * @return the boolean
	 */
	public boolean isBefore(final int minor) {
		return CURRENT_VERSION < minor;
	}
	
	/**
	 * Is before or equal boolean.
	 *
	 * @param minor the minor
	 * @return the boolean
	 */
	public boolean isBeforeOrEqual(final int minor) {
		return CURRENT_VERSION <= minor;
	}
	
	private int getCurrentVersion() {
		final Matcher matcher = Pattern.compile("(?<version>\\d+\\.\\d+)(?<patch>\\.\\d+)?")
		                               .matcher(Bukkit.getBukkitVersion());
		final StringBuilder stringBuilder = new StringBuilder(8);
		if (matcher.find()) {
			stringBuilder.append(matcher.group("version").replace(".", ""));
			final String patch = matcher.group("patch");
			if (patch == null) {
				stringBuilder.append('0');
			} else {
				stringBuilder.append(patch.replace(".", ""));
			}
		}
		try {
			return Integer.parseInt(stringBuilder.toString());
		} catch (final NumberFormatException ignored) {
			throw new RuntimeException("Could not retrieve server version!");
		}
	}
	
	private Material getSign() {
		if (CURRENT_VERSION < 1140) {
			return Material.valueOf("SIGN");
		} else {
			return Material.valueOf("OAK_SIGN");
		}
	}
	
	/**
	 * Gets target block.
	 *
	 * @param player   the player
	 * @param distance the distance
	 * @return the target block
	 */
	public Block getTargetBlock(final Player player, final int distance) {
		if (IS_TARGET_BLOCK_VERSION) {
			final Block targetBlock = player.getTargetBlockExact(distance);
			if (targetBlock != null) {
				return targetBlock;
			}
		} else {
			final BlockIterator iterator = new BlockIterator(player, distance);
			while (iterator.hasNext()) {
				final Block block = iterator.next();
				if (block.getType() != Material.AIR) {
					return block;
				}
			}
		}
		return player.getLocation().getBlock();
	}
	
	/**
	 * Gets enchantment name.
	 *
	 * @param enchantment the enchantment
	 * @return the enchantment name
	 */
	public String getEnchantmentName(final Enchantment enchantment) {
		if (IS_NAMESPACED_KEY_VERSION) {
			return enchantment.getKey()
			                  .getKey();
		} else {
			return enchantment.getName();
		}
	}
	
	/**
	 * Remove falling block after land.
	 *
	 * @param plugin       the plugin
	 * @param fallingBlock the falling block
	 */
	// TODO Check when FallingBlock#setCancelDrop(boolean cancel) is created
	public void removeFallingBlockAfterLand(final Plugin plugin, final Entity fallingBlock) {
		plugin.getServer()
		      .getScheduler()
		      .runTaskTimer(plugin, task -> {
			      if (!fallingBlock.isValid()) {
				      fallingBlock.getLocation()
				                  .getBlock()
				                  .setType(Material.AIR);
				      task.cancel();
			      }
		      }, 0L, 10L);
	}
	
	/**
	 * Returns online players from Bukkit API.
	 * This requires reflection, as return type changed in 1.8,
	 * and we want to avoid errors.
	 *
	 * @return  Online players from Bukkit API.
	 */
	@SuppressWarnings("unchecked")
	@SneakyThrows
	@NonNull
	public Player[] getOnlinePlayers() {
		Object players = Bukkit.class.getMethod("getOnlinePlayers").invoke(null);
		if (players instanceof Player[]) {
			// 1.7-
			return (Player[]) players;
		} else {
			// 1.8+
			return ((Collection<Player>)players).toArray(new Player[0]);
		}
	}
}
