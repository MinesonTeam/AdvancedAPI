package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.Registry;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;

import java.util.HashSet;
import java.util.Set;

/**
 * Class Attribute util.
 *
 * @author Hxncus
 * @since 1.0.1
 */
@UtilityClass
public class AttributeUtil {
	/**
	 * The Generic armor.
	 */
	public final double GENERIC_ARMOR = 0.0D;
	/**
	 * The Generic armor toughness.
	 */
	public final double GENERIC_ARMOR_TOUGHNESS = 0.0D;
	/**
	 * The Generic attack damage.
	 */
	public final double GENERIC_ATTACK_DAMAGE = 1.0D;
	/**
	 * The Generic attack knockback.
	 */
	public final double GENERIC_ATTACK_KNOCKBACK = 0.0D;
	/**
	 * The Generic attack speed.
	 */
	public final double GENERIC_ATTACK_SPEED = 4.0D;
	/**
	 * The Generic fall damage multiplier.
	 */
	public final double GENERIC_FALL_DAMAGE_MULTIPLIER = 1.0D;
	/**
	 * The Generic gravity.
	 */
	public final double GENERIC_GRAVITY = 0.08D;
	/**
	 * The Generic jump strength.
	 */
	public final double GENERIC_JUMP_STRENGTH = 0.41999998688697815D;
	/**
	 * The Generic knockback resistance.
	 */
	public final double GENERIC_KNOCKBACK_RESISTANCE = 0.0D;
	/**
	 * The Generic luck.
	 */
	public final double GENERIC_LUCK = 0.0D;
	/**
	 * The Generic max absorption.
	 */
	public final double GENERIC_MAX_ABSORPTION = 0.0D;
	/**
	 * The Generic max health.
	 */
	public final double GENERIC_MAX_HEALTH = 0.0D;
	/**
	 * The Generic movement speed.
	 */
	public final double GENERIC_MOVEMENT_SPEED = 0.10000000149011612D;
	/**
	 * The Generic safe fall distance.
	 */
	public final double GENERIC_SAFE_FALL_DISTANCE = 3.0D;
	/**
	 * The Generic scale.
	 */
	public final double GENERIC_SCALE = 1.0D;
	/**
	 * The Generic step height.
	 */
	public final double GENERIC_STEP_HEIGHT = 0.6D;
	/**
	 * The Player block break speed.
	 */
	public final double PLAYER_BLOCK_BREAK_SPEED = 1.0D;
	/**
	 * The Player block interaction range.
	 */
	public final double PLAYER_BLOCK_INTERACTION_RANGE = 4.5D;
	/**
	 * The Player entity interaction range.
	 */
	public final double PLAYER_ENTITY_INTERACTION_RANGE = 3.0D;
	/**
	 * The Zombie spawn reinforcements.
	 */
	public final double ZOMBIE_SPAWN_REINFORCEMENTS = 0.0929132596958409D;
	
	/**
	 * Gets attribute instances.
	 *
	 * @param attributable Entity that has attributable interface
	 * @return All attribute instance that attributable has
	 */
	public Set<AttributeInstance> getAttributeInstances(final Attributable attributable) {
		Registry<Attribute> attributes = Registry.ATTRIBUTE;
		final Set<AttributeInstance> attributeInstances = new HashSet<>((int) attributes.stream().count());
		for (final Attribute attribute : attributes) {
			final AttributeInstance attributeInstance = attributable.getAttribute(attribute);
			if (attributeInstance == null) {
				continue;
			}
			attributeInstances.add(attributeInstance);
		}
		return attributeInstances;
	}
}