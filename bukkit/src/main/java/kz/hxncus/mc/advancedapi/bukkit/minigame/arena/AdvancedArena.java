package kz.hxncus.mc.advancedapi.bukkit.minigame.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.arena.AbstractArena;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.arena.ArenaState;
import kz.hxncus.mc.advancedapi.api.bukkit.region.Region;
import kz.hxncus.mc.advancedapi.bukkit.region.AdvancedRegion;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AdvancedArena extends AbstractArena {
    public AdvancedArena(@NonNull final UUID uniqueId, final UUID creator, @NonNull final String name,
            @NonNull final World world, @NonNull final Region region, final long creationDate, @NonNull final ArenaState state,
            @NonNull final List<Location> spawnLocations) {
        super(uniqueId, creator, name, world, region, creationDate, state, spawnLocations);
    }

    public AdvancedArena(@NonNull final UUID uniqueId, final UUID creator, @NonNull final String name, @NonNull final World world, @NonNull final Region region) {
        super(uniqueId, creator, name, world, region);
    }

    @Override
    public void reset() {
        
    }
    
    @NonNull
    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("name", name);
        result.put("world", world.getUID().toString());
        result.put("region", region.serialize());
        result.put("uniqueId", uniqueId.toString());
        result.put("creator", creator == null ? null : creator.toString());
        result.put("creationDate", creationDate);
        result.put("state", state.name());

        final List<Location> spawnLocations = this.getSpawnLocations();
        final Map<String, Object> spawnLocationsMap = new LinkedHashMap<>();
        for (int i = 0; i < spawnLocations.size(); i++) {
            final Location location = spawnLocations.get(i);
            if (location != null) {
                spawnLocationsMap.put(String.valueOf(i), location.serialize());
            }
        }

        result.put("spawnLocations", spawnLocationsMap);
        return result;
    }
    
    @NonNull
    @SuppressWarnings("unchecked")
    public static AdvancedArena deserialize(@NonNull Map<String, Object> args) {
        final UUID uniqueId = UUID.fromString((String) args.get("uniqueId"));
        final String name = (String) args.get("name");
        final World world = Bukkit.getWorld(UUID.fromString((String) args.get("world")));
        final Region region = AdvancedRegion.deserialize((Map<String, Object>) args.get("region"));
        final UUID creator = args.get("creator") == null ? null : UUID.fromString((String) args.get("creator"));
        final long creationDate = Long.parseLong((String) args.get("creationDate"));
        final ArenaState state = ArenaState.valueOf((String) args.get("state"));

        final List<Location> spawnLocations = new ArrayList<>();
        for (Object value : ((Map<String, Object>) args.get("spawnLocations")).values()) {
            final Location location = Location.deserialize((Map<String, Object>) value);
            if (location != null) {
                spawnLocations.add(location);
            }
        }

        return new AdvancedArena(uniqueId, creator, name, world, region, creationDate, state, spawnLocations);
    }

    @NonNull
	public static AdvancedArena deserializeBySection(final ConfigurationSection section) {
		Map<String, Object> values = section.getValues(false);
		return AdvancedArena.deserialize(values);
	}
}
