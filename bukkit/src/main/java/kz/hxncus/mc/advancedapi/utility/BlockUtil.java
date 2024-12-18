package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.sign.Side;
import org.bukkit.persistence.PersistentDataType;

import com.google.common.base.Optional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Class Block util.
 *
 * @author Hxncus
 * @since 1.0.1
 */
@UtilityClass
public final class BlockUtil {
	public static final BlockFace[] BLOCK_FACES = BlockFace.values();
	public static final BlockFace[] SIGN_BLOCK_FACES = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST,
			BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST};
	public static final BlockFace[][] FENCE_BLOCK_FACES_VARIATIONS = new BlockFace[][]{
			// 0 Face
			{},
			// 1 Face
			{BlockFace.NORTH}, {BlockFace.EAST}, {BlockFace.SOUTH}, {BlockFace.WEST},
			// 2 Faces
			{BlockFace.NORTH, BlockFace.EAST}, {BlockFace.NORTH, BlockFace.SOUTH}, {BlockFace.NORTH, BlockFace.WEST},
			{BlockFace.EAST, BlockFace.SOUTH}, {BlockFace.EAST, BlockFace.WEST}, {BlockFace.SOUTH, BlockFace.WEST},
			// 3 Faces
			{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH}, {BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST},
			{BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH}, {BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST},
			// 4 Faces
			{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}};
	/**
	 * The Radix.
	 */
	public final int RADIX = 16;
	
	/**
	 * Is the player placed block?
	 *
	 * @param block the block
	 * @return the boolean
	 */
	public boolean isPlayerPlaced(final Block block) {
		final int hashCode = block.getLocation().hashCode();
		final String key = Integer.toString(hashCode, RADIX);
		final NamespacedKey namespacedKey = NamespacedKeyUtil.create(key);
		return block.getChunk().getPersistentDataContainer().has(namespacedKey, PersistentDataType.INTEGER);
	}
	
	public Set<Block> getVeinBlocks(Block startBlock, Predicate<Block> filter) {
		Set<Block> blocks = new HashSet<>();
		exploreVein(startBlock, filter, blocks);
		return blocks;
	}
	
	public <T extends Collection<Block>> void exploreVein(Block block, Predicate<Block> filter, T veinBlocks) {
		if (veinBlocks.contains(block) || !filter.test(block)) {
			return;
		}
		veinBlocks.add(block);
		for (BlockFace face : BlockFace.values()) {
			exploreVein(block.getRelative(face), filter, veinBlocks);
		}
	}
	
    public Set<BlockFace> getValidFaces(MultipleFacing multipleFacing) {
		Set<BlockFace> blockFaces = new HashSet<>();
		for (BlockFace blockFace : multipleFacing.getFaces()) {
			if (multipleFacing.hasFace(blockFace)) {
				blockFaces.add(blockFace);
			}
		}
		return blockFaces;
	}
	
	public void setBlockFaces(MultipleFacing multipleFacing, boolean has, BlockFace... blockFaces) {
		for (BlockFace blockFace : blockFaces) {
			multipleFacing.setFace(blockFace, has);
		}
	}
	
	public void setBlockFaces(MultipleFacing multipleFacing, boolean has, Set<BlockFace> blockFaces) {
		for (BlockFace blockFace : blockFaces) {
			multipleFacing.setFace(blockFace, has);
		}
	}
	
	public Optional<Sign> getSign(final Block block) {
		if (block.getState() instanceof Sign sign) {
			return Optional.of(sign);
		}
		return Optional.absent();
	}
	
	public boolean setSignLine(Block signBlock, Side side, int line, String text) {
		return BlockUtil.getSign(signBlock)
		                .transform(sign -> {
							sign.getSide(side).setLine(line, text);
							return true;
						}).or(false);
	}
	
	public boolean setSignLine(Block signBlock, int line, String text) {
		return BlockUtil.setSignLine(signBlock, Side.FRONT, line, text);
	}
	
	public String getSignLine(Block signBlock, Side side, int line) {
		return BlockUtil.getSign(signBlock)
		                .transform(sign -> {
			                return sign.getSide(side).getLine(line);
		                }).or("");
	}
	
	public String getSignLine(Block signBlock, int line) {
		return BlockUtil.getSignLine(signBlock, Side.FRONT, line);
	}
	
	public String[] getSignLines(Block signBlock, Side side) {
		return BlockUtil.getSign(signBlock)
		                .transform(sign -> {
			                return sign.getSide(side).getLines();
		                }).or(new String[4]);
	}
	
	public String[] getSignLines(Block signBlock) {
		return BlockUtil.getSignLines(signBlock, Side.FRONT);
	}
}