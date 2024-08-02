package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataType;

@UtilityClass
public class BlockUtil {
    public boolean isPlayerPlaced(Block block) {
        return block.getChunk().getPersistentDataContainer()
                    .has(NamespacedKeyUtil.create(Integer.toString(block.getLocation().hashCode(), 16)), PersistentDataType.INTEGER);
    }
}