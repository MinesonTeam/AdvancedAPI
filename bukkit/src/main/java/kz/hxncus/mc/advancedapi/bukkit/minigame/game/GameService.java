package kz.hxncus.mc.advancedapi.bukkit.minigame.game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.arena.Arena;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.game.Game;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import kz.hxncus.mc.advancedapi.utility.UUIDUtil;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class GameService {
    protected final Map<UUID, Game<? extends GameProfile>> games = new HashMap<>();

    public Game<?> createGame(@NonNull Function<UUID, Game<? extends GameProfile>> factory) {
        Game<? extends GameProfile> game = factory.apply(UUIDUtil.generateUniqueIdUntil(uniqueId -> this.games.containsKey(uniqueId)));
        this.games.put(game.getUniqueId(), game);
        return game;
    }

    public Game<?> createGame(@NonNull String name, @NonNull String description, @NonNull Arena arena, int minPlayers,
            int maxPlayers) {
        return this.createGame(uniqueId -> new AdvancedGame(uniqueId, name, description, arena, minPlayers, maxPlayers));
    }
}
