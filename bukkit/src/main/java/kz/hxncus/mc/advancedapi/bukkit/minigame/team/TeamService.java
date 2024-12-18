package kz.hxncus.mc.advancedapi.bukkit.minigame.team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.bukkit.plugin.Plugin;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.team.Team;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import kz.hxncus.mc.advancedapi.api.service.AbstractService;
import kz.hxncus.mc.advancedapi.utility.UUIDUtil;
import lombok.NonNull;

public class TeamService<P extends GameProfile> extends AbstractService {
    protected final Map<UUID, Team<? extends GameProfile>> teams = new HashMap<>();

    public TeamService(@NonNull Plugin plugin) {
        super(plugin);
    }

    @Override
    public void register() {
    }

    @Override
    public void unregister() {
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
