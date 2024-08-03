package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;

@UtilityClass
public class ServerUtil {
    public final Art[] ARTS = Art.values();
    public final Attribute[] ATTRIBUTES = Attribute.values();
    public final ChatColor[] CHAT_COLORS = ChatColor.values();
    public final DyeColor[] DYE_COLORS = DyeColor.values();
    public final Effect[] EFFECTS = Effect.values();
    public final EntityType[] ENTITY_TYPES = EntityType.values();
    public final EntityEffect[] ENTITY_EFFECTS = EntityEffect.values();
    public final Instrument[] INSTRUMENTS = Instrument.values();
    public final Material[] MATERIALS = Material.values();
    public final Particle[] PARTICLES = Particle.values();
    public final Sound[] SOUNDS = Sound.values();
    public final SoundCategory[] SOUND_CATEGORIES = SoundCategory.values();
    public final Statistic[] STATISTICS = Statistic.values();
    public final TreeType[] TREE_TYPES = TreeType.values();

    public final int MIN_COORD = -29999999;
    public final int MAX_COORD = 29999999;
}
