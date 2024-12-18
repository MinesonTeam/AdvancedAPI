package kz.hxncus.mc.advancedapi.api.bukkit.minigame.arena;

import org.bukkit.Location;
import org.bukkit.World;

import kz.hxncus.mc.advancedapi.api.bukkit.region.Region;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class AbstractArena implements Arena {
    @NonNull protected final UUID uniqueId;
    protected UUID creator;
    @NonNull protected String name;
    protected long creationDate;
    @NonNull protected World world;
    @NonNull protected Location lobbyLocation;
    @NonNull protected Region region;
    @NonNull protected List<Location> spawnLocations;
    @NonNull protected ArenaState state;
    
    protected AbstractArena(@NonNull final UUID uniqueId, final UUID creator, @NonNull final String name, @NonNull final World world, @NonNull final Region region) {
        this(uniqueId, creator, name, world, region, System.currentTimeMillis(), ArenaState.AVAILABLE, new ArrayList<>());
    }
    
    protected AbstractArena(@NonNull final UUID uniqueId, final UUID creator, @NonNull final String name,
            @NonNull final World world, @NonNull final Region region, final long creationDate, @NonNull final ArenaState state,
            @NonNull final List<Location> spawnLocations) {
        this.uniqueId = uniqueId;
        this.creator = creator;
        this.name = name;
        this.state = state;
        this.world = world;
        this.region = region;
        this.creationDate = creationDate;
        this.spawnLocations = spawnLocations;
    }
}
