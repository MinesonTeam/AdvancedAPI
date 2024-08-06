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

import java.util.Set;

@UtilityClass
public class RegionUtil {
	public final RegionContainer regionContainer = WorldGuard.getInstance()
	                                                         .getPlatform()
	                                                         .getRegionContainer();
	
	public Set<ProtectedRegion> getRegions(@NonNull final Location location) {
		return getApplicableRegions(location).getRegions();
	}
	
	public ApplicableRegionSet getApplicableRegions(@NonNull final Location location) {
		return getRegionManager(location.getWorld()).getApplicableRegions(BukkitAdapter.asBlockVector(location));
	}
	
	@NonNull
	public RegionManager getRegionManager(@NonNull final World world) {
		final RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(world));
		if (regionManager == null) {
			throw new RuntimeException("Region Manager is null");
		}
		return regionManager;
	}
	
	public void removeRegion(@NonNull final World world, @NonNull final String name) {
		getRegionManager(world).removeRegion(name);
	}
	
	public boolean hasRegion(@NonNull final World world, @NonNull final String name) {
		return getRegionManager(world).hasRegion(name);
	}
}
