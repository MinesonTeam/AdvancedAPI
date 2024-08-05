package kz.hxncus.mc.minesonapi.nms;

import kz.hxncus.mc.minesonapi.bukkit.nms.NMSChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.entity.Player;

public class Chunk_1_21_R1 implements NMSChunk {
    public ServerLevel nmsWorld;
    public LevelChunk nmsChunk;
    public World world;
    public int x = 0;
    public int z = 0;

    public void updateChunk(Player player) {
    }

    /**
     * Currently not works
     * @param world World
     * @param x block coordinate x
     * @param y block coordinate y
     * @param z block coordinate z
     * @param blockId blockId
     * @param applyPhysics do nearly blocks need to be updated
     */
    public void setBlockInNativeChunk(World world, int x, int y, int z, int blockId, boolean applyPhysics) {
        if (nmsWorld == null || this.world != world) {
            this.world = world;
            this.nmsWorld = ((CraftWorld) world).getHandle();
        }
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        if (this.x != chunkX || this.z != chunkZ) {
            this.x = chunkX;
            this.z = chunkZ;
            this.nmsChunk = nmsWorld.getChunk(chunkX, chunkZ);
        }
        LevelChunkSection[] sections = nmsChunk.getSections();
        int y4 = y >> 4;
        LevelChunkSection section = sections[y4];
        if (section == nmsChunk.getSection(y4)) {
            sections[y4] = sections[y4 << 4];
        }
        BlockState existing = section.getBlockState(x & 15, y & 15, z & 15);
        BlockPos blockPos = new BlockPos(x, y, z);
        section.getStates().set(x, y, z, Blocks.STONE.defaultBlockState());
        this.nmsWorld.notifyAndUpdatePhysics(blockPos, nmsChunk, existing, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), 3, 512);
    }
}
