package kz.hxncus.mc.advancedapi.api.bukkit.profile;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.game.Game;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.Party;

public interface GameProfile extends Profile {
    Party<? super GameProfile> getParty();
    void setParty(Party<? super GameProfile> party);
    Game<? super GameProfile> getGame();
    void setGame(Game<? super GameProfile> game);
    Map<UUID, Long> getPartyInvites();

    default Long getPartyInviteTime(UUID partyUniqueId) {
        return this.getPartyInvites().get(partyUniqueId);
    }

    default Long addPartyInvite(UUID partyUniqueId, long timestamp) {
        return this.addPartyInvite(partyUniqueId, timestamp, TimeUnit.MILLISECONDS);
    }

    default Long addPartyInvite(UUID partyUniqueId, long timestamp, TimeUnit unit) {
        return this.getPartyInvites().put(partyUniqueId, System.currentTimeMillis() + unit.toMillis(timestamp));
    }

    default Long removePartyInvite(UUID partyUniqueId) {
        return this.getPartyInvites().remove(partyUniqueId);
    }

    default boolean isInvited(UUID partyUniqueId) {
        return this.getPartyInvites().containsKey(partyUniqueId);
    }

    default boolean isPartyInviteExpired(UUID partyUniqueId) {
        return this.isInvited(partyUniqueId) && this.getPartyInviteTime(partyUniqueId) < System.currentTimeMillis();
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
