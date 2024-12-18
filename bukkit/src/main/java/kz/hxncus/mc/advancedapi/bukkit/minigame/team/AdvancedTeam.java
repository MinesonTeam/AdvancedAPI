package kz.hxncus.mc.advancedapi.bukkit.minigame.team;

import java.util.UUID;

import org.bukkit.ChatColor;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.team.AbstractTeam;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import lombok.NonNull;

public class AdvancedTeam extends AbstractTeam<GameProfile> {
    public AdvancedTeam(@NonNull UUID uniqueId, @NonNull ChatColor color, int maxPlayers) {
        super(uniqueId, color, maxPlayers);
    }
}
