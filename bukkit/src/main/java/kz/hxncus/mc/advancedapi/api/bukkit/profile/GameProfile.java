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
    Map<String, Long> getPartyInvites();

    default void clearExpiredPartyInvites() {
        this.getPartyInvites().entrySet().removeIf(entry -> entry.getValue() < System.currentTimeMillis());
    }

    default Long getPartyInviteTime(String partyName) {
        return this.getPartyInvites().get(partyName);
    }

    default Long addPartyInvite(String partyName, long timestamp) {
        return this.addPartyInvite(partyName, timestamp, TimeUnit.MILLISECONDS);
    }

    default Long addPartyInvite(String partyName, long timestamp, TimeUnit unit) {
        return this.getPartyInvites().put(partyName, System.currentTimeMillis() + unit.toMillis(timestamp));
    }

    default Long removePartyInvite(String partyName) {
        return this.getPartyInvites().remove(partyName);
    }

    default boolean isInvited(String partyName) {
        return this.getPartyInvites().containsKey(partyName);
    }

    default boolean isPartyInviteExpired(String partyName) {
        return this.isInvited(partyName) && this.getPartyInviteTime(partyName) < System.currentTimeMillis();
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
