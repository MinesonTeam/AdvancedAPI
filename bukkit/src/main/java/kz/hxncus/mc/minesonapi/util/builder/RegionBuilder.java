package kz.hxncus.mc.minesonapi.util.builder;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.function.Consumer;

/**
 * Class Region builder.
 *
 * @author Hxncus
 * @since 1.0.0
 */
@ToString
public class RegionBuilder {
	/**
	 * The Region container.
	 */
	public final RegionContainer regionContainer = WorldGuard.getInstance()
	                                                         .getPlatform()
	                                                         .getRegionContainer();
	private final ProtectedCuboidRegion cuboidRegion;
	private final RegionManager regionManager;
	
	/**
	 * Instantiates a new Region builder.
	 *
	 * @param regionName the region name
	 * @param world      the world
	 * @param pos1       the pos 1
	 * @param pos2       the pos 2
	 */
	public RegionBuilder(@NonNull final String regionName, @NonNull final World world, @NonNull final BlockVector3 pos1, @NonNull final BlockVector3 pos2) {
		this.regionManager = this.regionContainer.get(BukkitAdapter.adapt(world));
		this.cuboidRegion = new ProtectedCuboidRegion(regionName, pos1, pos2);
	}
	
	/**
	 * Instantiates a new Region builder.
	 *
	 * @param regionName the region name
	 * @param world      the world
	 * @param pos1       the pos 1
	 * @param pos2       the pos 2
	 */
	public RegionBuilder(@NonNull final String regionName, @NonNull final World world, @NonNull final Location pos1, @NonNull final Location pos2) {
		this(regionName, world, BukkitAdapter.asBlockVector(pos1), BukkitAdapter.asBlockVector(pos2));
	}
	
	/**
	 * Sets flag.
	 *
	 * @param <T>   the type parameter
	 * @param <V>   the type parameter
	 * @param flag  the flag
	 * @param value the value
	 * @return the flag
	 */
	public <T extends Flag<V>, V> RegionBuilder setFlag(final T flag, final V value) {
		return this.editRegion(region -> region.setFlag(flag, value));
	}
	
	/**
	 * Edit region region builder.
	 *
	 * @param consumer the consumer
	 * @return the region builder
	 */
	public RegionBuilder editRegion(final Consumer<? super ProtectedCuboidRegion> consumer) {
		consumer.accept(this.cuboidRegion);
		return this;
	}
	
	/**
	 * Remove region region builder.
	 *
	 * @return the region builder
	 */
	public RegionBuilder removeRegion() {
		return this.editRegion(region -> this.regionManager.removeRegion(region.getId()));
	}
	
	/**
	 * Build region builder.
	 *
	 * @return the region builder
	 */
	public RegionBuilder build() {
		return this.editRegion(this.regionManager::addRegion);
	}
}
