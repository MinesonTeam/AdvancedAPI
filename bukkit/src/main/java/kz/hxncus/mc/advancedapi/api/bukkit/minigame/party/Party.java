package kz.hxncus.mc.advancedapi.api.bukkit.minigame.party;

import lombok.NonNull;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.Profile;
import kz.hxncus.mc.advancedapi.api.collective.Collective;
import kz.hxncus.mc.advancedapi.api.leader.Leader;

/**
 * Интерфейс для работы с пати игроков
 */
public interface Party<P extends GameProfile> extends Collective<P>, Leader<P> { 
    boolean isPublic();

    void setPublic(boolean isPublic);

    PartyState getState();

    void setState(PartyState state);

    /**
     * Удалить игрока из пати
     */
    @Override
    default boolean removeProfile(@NonNull P profile) {
        if (this.getProfiles().remove(profile)) {
            if (profile.equals(this.getLeader())) {
                if (this.getProfiles().isEmpty()) {
                    this.getProfiles().add(profile);
                    this.setLeader(profile);
                    return false;
                } else {
                    this.setLeader(this.getProfiles().get(0));
                    return true;
                }
            }
            profile.setParty(null);
            return true;
        }
        return false;
    }

    default void removeAll() {
        this.getProfiles().forEach(profile -> {
            if (!this.isLeader(profile)) {
                profile.setParty(null);
            }
        });
        this.getProfiles().clear();
        this.getProfiles().add(this.getLeader());
    }

    default void disband() {
        this.getProfiles().forEach(profile -> {
            if (!this.isLeader(profile)) {
                profile.setParty(null);
            }
        });
        this.getProfiles().clear();
        this.setState(PartyState.DISBANDED);
        this.setPublic(false);
        this.setLeader(null);
    }
}
