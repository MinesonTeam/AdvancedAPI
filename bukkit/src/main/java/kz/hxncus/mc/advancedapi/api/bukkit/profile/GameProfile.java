package kz.hxncus.mc.advancedapi.api.bukkit.profile;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.game.Game;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.Party;

public interface GameProfile extends Profile {
    Party<? super GameProfile> getParty();
    void setParty(Party<? super GameProfile> party);
    Game<? super GameProfile> getGame();
    void setGame(Game<? super GameProfile> game);

    default boolean isInParty() {
        return this.getParty() != null && this.getParty().hasProfile(this);
    }

    default boolean isInGame() {
        return this.getGame() != null && this.getGame().hasProfile(this);
    }
}
