package kz.hxncus.mc.advancedapi.api.bukkit.profile;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.game.Game;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.Party;

public interface GameProfile extends Profile {
    Party<? super GameProfile> getParty();
    void setParty(Party<? super GameProfile> party);
    Game<? super GameProfile> getGame();
    void setGame(Game<? super GameProfile> game);

    default boolean isInParty() {
        Party<? super GameProfile> party = this.getParty();
        return party != null && party.hasProfile(this);
    }

    default boolean isInGame() {
        Game<? super GameProfile> game = this.getGame();
        return game != null && game.hasProfile(this);
    }
}
