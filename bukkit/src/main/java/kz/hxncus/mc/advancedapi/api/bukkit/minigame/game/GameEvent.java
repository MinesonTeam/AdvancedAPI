package kz.hxncus.mc.advancedapi.api.bukkit.minigame.game;

import org.bukkit.event.Event;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class GameEvent<P extends GameProfile> extends Event {
    protected Game<P> game;

    public GameEvent(@NonNull final Game<P> game) {
        this.game = game;
    }
}
