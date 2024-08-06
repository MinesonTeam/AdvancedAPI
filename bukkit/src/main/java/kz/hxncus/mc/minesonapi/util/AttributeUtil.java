package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class AttributeUtil {
	public final double GENERIC_ARMOR = 0.0D;
	public final double GENERIC_ARMOR_TOUGHNESS = 0.0D;
	public final double GENERIC_ATTACK_DAMAGE = 1.0D;
	public final double GENERIC_ATTACK_KNOCKBACK = 0.0D;
	public final double GENERIC_ATTACK_SPEED = 4.0D;
	public final double GENERIC_FALL_DAMAGE_MULTIPLIER = 1.0D;
	public final double GENERIC_GRAVITY = 0.08D;
	public final double GENERIC_JUMP_STRENGTH = 0.41999998688697815D;
	public final double GENERIC_KNOCKBACK_RESISTANCE = 0.0D;
	public final double GENERIC_LUCK = 0.0D;
	public final double GENERIC_MAX_ABSORPTION = 0.0D;
	public final double GENERIC_MAX_HEALTH = 0.0D;
	public final double GENERIC_MOVEMENT_SPEED = 0.10000000149011612D;
	public final double GENERIC_SAFE_FALL_DISTANCE = 3.0D;
	public final double GENERIC_SCALE = 1.0D;
	public final double GENERIC_STEP_HEIGHT = 0.6D;
	public final double PLAYER_BLOCK_BREAK_SPEED = 1.0D;
	public final double PLAYER_BLOCK_INTERACTION_RANGE = 4.5D;
	public final double PLAYER_ENTITY_INTERACTION_RANGE = 3.0D;
	public final double ZOMBIE_SPAWN_REINFORCEMENTS = 0.0929132596958409D;
	
	/**
	 * @param attributable Entity that has attributable interface
	 * @return All attribute instance that attributable has
	 */
	public Set<AttributeInstance> getAttributeInstances(final Attributable attributable) {
		final Set<AttributeInstance> attributeInstances = new HashSet<>(ServerUtil.ATTRIBUTES.length);
		for (final Attribute attribute : ServerUtil.ATTRIBUTES) {
			final AttributeInstance attributeInstance = attributable.getAttribute(attribute);
			if (attributeInstance == null) {
				continue;
			}
			attributeInstances.add(attributeInstance);
		}
		return attributeInstances;
	}
}