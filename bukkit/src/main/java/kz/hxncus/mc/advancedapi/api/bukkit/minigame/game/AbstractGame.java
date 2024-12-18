package kz.hxncus.mc.advancedapi.api.bukkit.minigame.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.arena.Arena;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.team.Team;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;

@Getter
@Setter
public abstract class AbstractGame<P extends GameProfile> implements Game<P> {
    protected final UUID uniqueId;
    protected String name;
    protected String description;
    protected Arena arena;
    protected int minPlayers;
    protected int maxPlayers;
    protected final List<Team<P>> teams;
    protected GameState state;
    
    protected AbstractGame(@NonNull UUID uniqueId, @NonNull String name, @NonNull String description, 
                         @NonNull Arena arena, int minPlayers, int maxPlayers) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.description = description;
        this.arena = arena;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.teams = new ArrayList<>();
        this.state = GameState.WAITING;
    }
}
