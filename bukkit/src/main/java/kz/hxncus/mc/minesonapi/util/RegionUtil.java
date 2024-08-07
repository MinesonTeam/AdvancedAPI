package kz.hxncus.mc.minesonapi.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

/**
 * The type Region util.
 * @author Hxncus
 * @since  1.0.0
 */
@UtilityClass
public class RegionUtil {
	/**
	 * The Region container.
	 */
	public final RegionContainer regionContainer = WorldGuard.getInstance()
	                                                         .getPlatform()
	                                                         .getRegionContainer();
	
	/**
	 * Gets regions.
	 *
	 * @param location the location
	 * @return the regions
	 */
	public Set<ProtectedRegion> getRegions(@NonNull final Location location) {
		final ApplicableRegionSet applicableRegions = getApplicableRegions(location);
		if (applicableRegions == null) {
			return Collections.emptySet();
		}
		return applicableRegions.getRegions();
	}
	
	/**
	 * Gets applicable regions.
	 *
	 * @param location the location
	 * @return the applicable regions
	 */
	@Nullable
	public ApplicableRegionSet getApplicableRegions(@NonNull final Location location) {
		final World world = location.getWorld();
		if (world == null) {
			return null;
		}
		return getRegionManager(world).getApplicableRegions(BukkitAdapter.asBlockVector(location));
	}
	
	/**
	 * Gets region manager.
	 *
	 * @param world the world
	 * @return the region manager
	 */
	@NonNull
	public RegionManager getRegionManager(@NonNull final World world) {
		final RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(world));
		if (regionManager == null) {
			throw new RuntimeException("Region Manager is null");
		}
		return regionManager;
	}
	
	/**
	 * Remove region.
	 *
	 * @param world the world
	 * @param name  the name
	 */
	public void removeRegion(@NonNull final World world, @NonNull final String name) {
		getRegionManager(world).removeRegion(name);
	}
	
	/**
	 * Has a region boolean.
	 *
	 * @param world the world
	 * @param name  the name
	 * @return the boolean
	 */
	public boolean hasRegion(@NonNull final World world, @NonNull final String name) {
		return getRegionManager(world).hasRegion(name);
	}
}
