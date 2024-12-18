package kz.hxncus.mc.advancedapi.api.collective;

import java.util.List;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.Profile;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractCollective<P extends Profile> implements Collective<P> {
    protected String name;
    protected int maxProfiles;
    protected final List<P> profiles;

    public AbstractCollective(@NonNull final String name, final int maxProfiles, @NonNull final List<P> profiles) {
        this.name = name;
        this.maxProfiles = maxProfiles;
        this.profiles = profiles;
    }
}
