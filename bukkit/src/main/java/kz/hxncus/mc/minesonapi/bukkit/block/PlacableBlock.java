package kz.hxncus.mc.minesonapi.bukkit.block;

import kz.hxncus.mc.minesonapi.api.bukkit.nms.NMSChunk;
import kz.hxncus.mc.minesonapi.api.bukkit.workload.Workload;
import kz.hxncus.mc.minesonapi.bukkit.nms.NMSHandler;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.UUID;

/**
 * The type-Placable block.
 * @author Hxncus
 * @since  1.0.1
 */
@ToString
@AllArgsConstructor
public class PlacableBlock implements Workload {
	private final NMSChunk chunk = NMSHandler.getChunk();
	private final UUID worldUuid;
	private final int blockX;
	private final int blockY;
	private final int blockZ;
	private final Material material;
	
	@Override
	public void compute() {
		final World world = Bukkit.getWorld(this.worldUuid);
		if (world == null) {
			return;
		}
		this.chunk.setBlockInNativeChunk(world, this.blockX, this.blockY, this.blockZ, 0, false);
	}
	
}
