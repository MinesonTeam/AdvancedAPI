package kz.hxncus.mc.advancedapi.bukkit.minigame.arena;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.arena.Arena;
import kz.hxncus.mc.advancedapi.api.bukkit.region.Region;
import kz.hxncus.mc.advancedapi.utility.UUIDUtil;
import lombok.NonNull;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class ArenaController {
    protected final Map<UUID, Arena> arenas = new HashMap<>();

    public ArenaController(@NonNull Plugin plugin) {
    }

    public Arena createArena(@NonNull Function<UUID, Arena> factory) {
        Arena arena = factory.apply(UUIDUtil.generateUniqueIdUntil(uniqueId -> this.arenas.containsKey(uniqueId)));
        this.arenas.put(arena.getUniqueId(), arena);
        return arena;
    }

    public Arena createArena(UUID creator, @NonNull String name, @NonNull World world, @NonNull Region region) {
        return this.createArena(uniqueId -> new AdvancedArena(uniqueId, creator, name, world, region));
    }

    public Arena getArena(@NonNull UUID uniqueId) {
        return this.arenas.get(uniqueId);
    }
}
