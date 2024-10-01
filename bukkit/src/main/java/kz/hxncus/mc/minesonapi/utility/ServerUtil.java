package kz.hxncus.mc.minesonapi.utility;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * The type Server util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public class ServerUtil {
	/**
	 * The Arts.
	 */
	public final Art[] ARTS = Art.values();
	/**
	 * The Attributes.
	 */
	public final Attribute[] ATTRIBUTES = Attribute.values();
	/**
	 * The Chat colors.
	 */
	public final ChatColor[] CHAT_COLORS = ChatColor.values();
	/**
	 * The Dye colors.
	 */
	public final DyeColor[] DYE_COLORS = DyeColor.values();
	/**
	 * The Effects.
	 */
	public final Effect[] EFFECTS = Effect.values();
	/**
	 * The Entity types.
	 */
	public final EntityType[] ENTITY_TYPES = EntityType.values();
	/**
	 * The Entity effects.
	 */
	public final EntityEffect[] ENTITY_EFFECTS = EntityEffect.values();
	/**
	 * The Instruments.
	 */
	public final Instrument[] INSTRUMENTS = Instrument.values();
	/**
	 * The Materials.
	 */
	public final Material[] MATERIALS = Material.values();
	/**
	 * The Particles.
	 */
	public final Particle[] PARTICLES = Particle.values();
	/**
	 * The Sounds.
	 */
	public final Sound[] SOUNDS = Sound.values();
	/**
	 * The Sound categories.
	 */
	public final SoundCategory[] SOUND_CATEGORIES = SoundCategory.values();
	/**
	 * The Statistics.
	 */
	public final Statistic[] STATISTICS = Statistic.values();
	/**
	 * The Tree types.
	 */
	public final TreeType[] TREE_TYPES = TreeType.values();
	
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
