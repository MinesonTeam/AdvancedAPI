package kz.hxncus.mc.advancedapi.bukkit.minigame.party;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.Party;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import kz.hxncus.mc.advancedapi.utility.UUIDUtil;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class PartyController {
    protected final Map<UUID, Party<? extends GameProfile>> parties = new HashMap<>();
    
    public PartyController(@NonNull Plugin plugin) {
    }

    public Party<? extends GameProfile> getParty(@NonNull UUID uuid) {
        return this.parties.get(uuid);
    }

    public Party<? extends GameProfile> createParty(@NonNull Function<UUID, Party<? extends GameProfile>> factory) {
        Party<? extends GameProfile> party = factory.apply(UUIDUtil.generateUniqueIdUntil(uniqueId -> this.parties.containsKey(uniqueId)));
        this.parties.put(party.getUniqueId(), party);
        return party;
    }
}
