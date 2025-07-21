package kz.hxncus.mc.advancedapi.bukkit.minigame.team;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.team.Team;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import kz.hxncus.mc.advancedapi.utility.UUIDUtil;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class TeamController {
    protected final Map<UUID, Team<? extends GameProfile>> teams = new HashMap<>();

    public TeamController(@NonNull Plugin plugin) {
    }

    public Team<? extends GameProfile> getTeam(@NonNull UUID uuid) {
        return this.teams.get(uuid);
    }

    public Team<? extends GameProfile> createTeam(@NonNull Function<UUID, Team<? extends GameProfile>> factory) {
        Team<? extends GameProfile> team = factory.apply(UUIDUtil.generateUniqueIdUntil(uniqueId -> this.teams.containsKey(uniqueId)));
        this.teams.put(team.getUniqueId(), team);
        return team;
    }
}
