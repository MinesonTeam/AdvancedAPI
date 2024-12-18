package kz.hxncus.mc.advancedapi.bukkit.minigame.game;

import java.util.UUID;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.arena.Arena;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.game.AbstractGame;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.game.GameState;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import lombok.NonNull;

public class AdvancedGame extends AbstractGame<GameProfile> {
    public AdvancedGame(@NonNull UUID uniqueId, @NonNull String name, @NonNull String description, @NonNull Arena arena,
            int minPlayers, int maxPlayers) {
        super(uniqueId, name, description, arena, minPlayers, maxPlayers);
    }

    @Override
    public void start() {
        this.setState(GameState.STARTING);
        
        this.setState(GameState.RUNNING);
    }

    @Override
    public void stop() {
        this.setState(GameState.ENDING);
        
        this.setState(GameState.FINISHED);
    }
}
