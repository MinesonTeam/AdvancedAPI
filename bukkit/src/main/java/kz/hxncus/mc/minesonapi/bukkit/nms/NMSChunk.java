package kz.hxncus.mc.minesonapi.bukkit.nms;

import org.bukkit.World;

public interface NMSChunk {
    void setBlockInNativeChunk(World world, int x, int y, int z, int blockId, boolean applyPhysics);
}
