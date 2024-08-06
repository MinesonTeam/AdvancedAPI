package kz.hxncus.mc.minesonapi.util.builder;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.function.Consumer;

public class RegionBuilder {
	public final RegionContainer regionContainer = WorldGuard.getInstance()
	                                                         .getPlatform()
	                                                         .getRegionContainer();
	private final ProtectedCuboidRegion cuboidRegion;
	private final RegionManager regionManager;
	
	public RegionBuilder(@NonNull final String regionName, @NonNull final Location pos1, @NonNull final Location pos2) {
		this(regionName, pos1.getWorld(), BukkitAdapter.asBlockVector(pos1), BukkitAdapter.asBlockVector(pos2));
	}
	
	public RegionBuilder(@NonNull final String regionName, @NonNull final World world, @NonNull final BlockVector3 pos1, @NonNull final BlockVector3 pos2) {
		this.regionManager = this.regionContainer.get(BukkitAdapter.adapt(world));
		this.cuboidRegion = new ProtectedCuboidRegion(regionName, pos1, pos2);
	}
	
	public RegionBuilder(@NonNull final String regionName, @NonNull final World world, @NonNull final Location pos1, @NonNull final Location pos2) {
		this(regionName, world, BukkitAdapter.asBlockVector(pos1), BukkitAdapter.asBlockVector(pos2));
	}
	
	public <T extends Flag<V>, V> RegionBuilder setFlag(final T flag, final V value) {
		return this.editRegion(region -> region.setFlag(flag, value));
	}
	
	public RegionBuilder editRegion(final Consumer<ProtectedCuboidRegion> consumer) {
		consumer.accept(this.cuboidRegion);
		return this;
	}
	
	public RegionBuilder removeRegion() {
		return this.editRegion(region -> this.regionManager.removeRegion(region.getId()));
	}
	
	public RegionBuilder build() {
		return this.editRegion(this.regionManager::addRegion);
	}
}
