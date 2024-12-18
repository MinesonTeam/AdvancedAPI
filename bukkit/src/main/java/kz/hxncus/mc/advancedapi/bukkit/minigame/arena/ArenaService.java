package kz.hxncus.mc.advancedapi.bukkit.minigame.arena;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.arena.Arena;
import kz.hxncus.mc.advancedapi.api.bukkit.region.Region;
import kz.hxncus.mc.advancedapi.api.service.AbstractService;
import kz.hxncus.mc.advancedapi.utility.UUIDUtil;
import lombok.NonNull;

public class ArenaService extends AbstractService {
    protected final Map<UUID, Arena> arenas = new HashMap<>();

    public ArenaService(@NonNull Plugin plugin) {
        super(plugin);
    }

    @Override
    public void register() {
        
    }

    @Override
    public void unregister() {
        
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
