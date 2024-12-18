package kz.hxncus.mc.advancedapi.bukkit.minigame.party;

import java.util.UUID;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.AbstractParty;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import kz.hxncus.mc.advancedapi.bukkit.profile.AdvancedGameProfile;
import lombok.NonNull;

public class AdvancedParty extends AbstractParty<GameProfile> {
    public AdvancedParty(@NonNull UUID uniqueId, @NonNull String name, @NonNull AdvancedGameProfile leader, int maxPlayers) {
        super(uniqueId, name, leader, maxPlayers);
    }
}
