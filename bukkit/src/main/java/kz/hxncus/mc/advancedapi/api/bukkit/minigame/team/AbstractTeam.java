package kz.hxncus.mc.advancedapi.api.bukkit.minigame.team;

import org.bukkit.ChatColor;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class AbstractTeam<P extends GameProfile> implements Team<P> {
    protected final UUID uniqueId;
    protected String name;
    protected ChatColor color;
    protected String prefix;
    protected String suffix;
    protected int maxProfiles;
    protected final List<P> profiles;
    
    protected AbstractTeam(@NonNull UUID uniqueId, @NonNull ChatColor color, int maxProfiles) {
        this.uniqueId = uniqueId;
        this.name = color.name();
        this.color = color;
        this.prefix = String.valueOf(color.getChar());
        this.suffix = "";
        this.maxProfiles = maxProfiles;
        this.profiles = new ArrayList<>();
    }
}
