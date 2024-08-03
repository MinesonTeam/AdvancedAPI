package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class VersionUtil {
    public final int CURRENT_VERSION = getCurrentVersion();
    public final boolean IS_PDC_VERSION = CURRENT_VERSION >= 1140;
    public final boolean IS_HEX_VERSION = CURRENT_VERSION >= 1160;
    public final boolean IS_TARGET_BLOCK_VERSION = CURRENT_VERSION >= 1140;
    public final boolean IS_NAMESPACED_KEY_VERSION = CURRENT_VERSION >= 1120;
    public final boolean IS_SPAWN_EGG_META_VERSION = CURRENT_VERSION >= 1110;
    public final boolean IS_POTION_COLOR_VERSION = CURRENT_VERSION >= 1110;
    public final boolean IS_POTION_DATA_VERSION = CURRENT_VERSION >= 190;
    public final Material SIGN = getSign();

    public boolean is(int minor) {
        return CURRENT_VERSION == minor;
    }

    public boolean after(int minor) {
        return CURRENT_VERSION > minor;
    }

    public boolean afterOrEqual(int minor) {
        return CURRENT_VERSION >= minor;
    }

    public boolean before(int minor) {
        return CURRENT_VERSION < minor;
    }

    public boolean beforeOrEqual(int minor) {
        return CURRENT_VERSION <= minor;
    }

    private int getCurrentVersion() {
        Matcher matcher = Pattern.compile("(?<version>\\d+\\.\\d+)(?<patch>\\.\\d+)?").matcher(Bukkit.getBukkitVersion());
        StringBuilder stringBuilder = new StringBuilder();
        if (matcher.find()) {
            stringBuilder.append(matcher.group("version")
                                        .replace(".", ""));
            String patch = matcher.group("patch");
            if (patch == null) {
                stringBuilder.append('0');
            } else {
                stringBuilder.append(patch.replace(".", ""));
            }
        }
        try {
            return Integer.parseInt(stringBuilder.toString());
        } catch (NumberFormatException ignored) {
            throw new RuntimeException("Could not retrieve server version!");
        }
    }

    private Material getSign() {
        if (VersionUtil.CURRENT_VERSION < 1140) {
            return Material.valueOf("SIGN");
        } else {
            return Material.valueOf("OAK_SIGN");
        }
    }

    public Block getTargetBlock(Player player, int distance) {
        if (IS_TARGET_BLOCK_VERSION) {
            Block targetBlock = player.getTargetBlockExact(distance);
            if (targetBlock != null) {
                return targetBlock;
            }
        } else {
            BlockIterator iterator = new BlockIterator(player, distance);
            while (iterator.hasNext()) {
                Block block = iterator.next();
                if (block.getType() != Material.AIR) {
                    return block;
                }
            }
        }
        return player.getLocation().getBlock();
    }

    public String getEnchantmentName(Enchantment enchantment) {
        if (IS_NAMESPACED_KEY_VERSION) {
            return enchantment.getKey().getKey();
        } else {
            return enchantment.getName();
        }
    }

    // TODO Check when FallingBlock#setCancelDrop(boolean cancel) is created
    public void removeFallingBlockAfterLand(Plugin plugin, FallingBlock fallingBlock) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, task -> {
            if (!fallingBlock.isValid()) {
                fallingBlock.getLocation().getBlock().setType(Material.AIR);
                task.cancel();
            }
        }, 0L, 10L);
    }
}
