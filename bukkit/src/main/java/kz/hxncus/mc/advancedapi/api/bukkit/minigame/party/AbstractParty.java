package kz.hxncus.mc.advancedapi.api.bukkit.minigame.party;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;

@Getter
@Setter
public abstract class AbstractParty<P extends GameProfile> implements Party<P> {
    protected final UUID uniqueId;
    protected String name;
    protected P leader;
    protected boolean isPublic;
    protected int maxProfiles;
    protected final List<P> profiles;
    protected PartyState state;
    
    protected AbstractParty(@NonNull UUID uniqueId, @NonNull String name, @NonNull P leader, int maxProfiles) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.leader = leader;
        this.isPublic = false;
        this.profiles = new ArrayList<>();
        this.profiles.add(leader);
        this.maxProfiles = maxProfiles;
        this.state = PartyState.CLOSED;
    }

    @Override
    public boolean setLeader(@NonNull P profile) {
        boolean hasProfile = this.hasProfile(profile);
        if (hasProfile) {
            this.leader = profile;
        }
        return hasProfile;
    }
}
