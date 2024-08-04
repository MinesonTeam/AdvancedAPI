package kz.hxncus.mc.minesonapi.nms;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.bukkit.nms.NMSChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;

public class Chunk_1_20_R3 implements NMSChunk {
    public ServerLevel nmsWorld;
    public LevelChunk nmsChunk;
    public World world;
    public int x = 0;
    public int z = 0;

    public void setBlockInNativeChunk(World world, int x, int y, int z, int blockId, boolean applyPhysics) {
        if (nmsWorld == null || this.world != world) {
            this.world = world;
            this.nmsWorld = ((CraftWorld) world).getHandle();
        }
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        if (x != chunkX || z != chunkZ) {
            this.x = chunkX;
            this.z = chunkZ;
            this.nmsChunk = nmsWorld.getChunkAt(new BlockPos(chunkX, 0, chunkZ));
        }
        BlockPos blockPos = new BlockPos(x, y, z);
        nmsChunk.setBlockState(blockPos, Blocks.DIRT.defaultBlockState(), applyPhysics, true);
        MinesonAPI.get().getLogger().info("SETBLOCKSTATE: " + blockPos);
        MinesonAPI.get().getLogger().info("ISAIR " + Blocks.DIRT.defaultBlockState().isAir());
    }
}
