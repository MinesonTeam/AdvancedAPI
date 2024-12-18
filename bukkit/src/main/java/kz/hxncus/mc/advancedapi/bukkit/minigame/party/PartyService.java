package kz.hxncus.mc.advancedapi.bukkit.minigame.party;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.bukkit.plugin.Plugin;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.Party;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import kz.hxncus.mc.advancedapi.api.service.AbstractService;
import kz.hxncus.mc.advancedapi.utility.UUIDUtil;
import lombok.NonNull;

public class PartyService extends AbstractService {
    protected final Map<UUID, Party<? extends GameProfile>> parties = new HashMap<>();
    
    public PartyService(@NonNull Plugin plugin) {
        super(plugin);
    }

    @Override
    public void register() {
    }

    @Override
    public void unregister() {
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
