package kz.hxncus.mc.advancedapi.api.bukkit.profile;

import java.util.Map;
import java.util.UUID;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.game.Game;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.Party;

public interface GameProfile extends Profile {
    Party<? super GameProfile> getParty();
    void setParty(Party<? super GameProfile> party);
    Game<? super GameProfile> getGame();
    void setGame(Game<? super GameProfile> game);
    Map<UUID, Long> getPartyInvites();

    default Long getPartyInviteTime(UUID inviter) {
        return this.getPartyInvites().get(inviter);
    }

    default Long addPartyInvite(UUID inviter, long timestamp) {
        return this.getPartyInvites().put(inviter, timestamp);
    }

    default void removePartyInvite(UUID inviter) {
        this.getPartyInvites().remove(inviter);
    }

    default boolean isInvited(UUID inviter) {
        return this.getPartyInvites().containsKey(inviter);
    }

    default boolean isPartyInviteExpired(UUID inviter) {
        return this.isInvited(inviter) && this.getPartyInviteTime(inviter) < System.currentTimeMillis();
    }

    default boolean isPartyOwner() {
        Party<? super GameProfile> party = this.getParty();
        return party != null && party.getLeader() == this;
    }

    default boolean isInParty() {
        Party<? super GameProfile> party = this.getParty();
        return party != null && party.hasProfile(this);
    }

    default boolean isInGame() {
        Game<? super GameProfile> game = this.getGame();
        return game != null && game.hasProfile(this);
    }
}
