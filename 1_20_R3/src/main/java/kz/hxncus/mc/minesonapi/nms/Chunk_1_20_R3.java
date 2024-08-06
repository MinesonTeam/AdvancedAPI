package kz.hxncus.mc.minesonapi.nms;

import kz.hxncus.mc.minesonapi.bukkit.nms.NMSChunk;
import lombok.ToString;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;

/**
 * The type Chunk 1 20 r 3.
 */
@ToString
public class Chunk_1_20_R3 implements NMSChunk {
    public static final int J = 512;
    /**
     * The Nms world.
     */
    public ServerLevel nmsWorld;
    /**
     * The Nms chunk.
     */
    public LevelChunk nmsChunk;
    /**
     * The World.
     */
    public World world;
    /**
     * The X.
     */
    public int x;
    /**
     * The Z.
     */
    public int z;

    /**
     * @param world        World
     * @param x            block coordinate x
     * @param y            block coordinate y
     * @param z            block coordinate z
     * @param blockId      blockId
     * @param applyPhysics do nearly blocks need to be updated?
     */
    public void setBlockInNativeChunk(final World world, final int x, final int y, final int z, final int blockId, final boolean applyPhysics) {
        if (this.nmsWorld == null || this.world != world) {
            this.world = world;
            this.nmsWorld = ((CraftWorld) world).getHandle();
        }
        final int chunkX = x >> 4;
        final int chunkZ = z >> 4;
        if (this.x != chunkX || this.z != chunkZ) {
            this.x = chunkX;
            this.z = chunkZ;
            this.nmsChunk = this.nmsWorld.getChunk(chunkX, chunkZ);
        }
        final LevelChunkSection[] sections = this.nmsChunk.getSections();
        final int y4 = y >> 4;
        final LevelChunkSection section = sections[y4];
        if (section == this.nmsChunk.getSection(y4)) {
            sections[y4] = sections[y4 << 4];
        }
        final BlockState existing = section.getBlockState(x & 15, y & 15, z & 15);
        final BlockPos blockPos = new BlockPos(x, y, z);
        section.getStates()
               .set(x, y, z, Blocks.STONE.defaultBlockState());
        this.nmsWorld.notifyAndUpdatePhysics(blockPos, this.nmsChunk, existing, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), 3, J);
    }
}
