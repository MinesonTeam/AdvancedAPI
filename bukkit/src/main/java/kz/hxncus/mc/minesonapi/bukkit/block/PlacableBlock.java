package kz.hxncus.mc.minesonapi.bukkit.block;

import kz.hxncus.mc.minesonapi.bukkit.nms.NMSHandler;
import kz.hxncus.mc.minesonapi.bukkit.workload.Workload;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.UUID;

@AllArgsConstructor
public class PlacableBlock implements Workload {
    private final UUID worldUuid;
    private final int blockX;
    private final int blockY;
    private final int blockZ;
    private final Material material;

    @Override
    public void compute() {
        World world = Bukkit.getWorld(worldUuid);
        if (world == null) {
            return;
        }
        NMSHandler.getChunk().setBlockInNativeChunk(world, blockX, blockY, blockZ, 0, false);
    }

}
