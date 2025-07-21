package kz.hxncus.mc.advancedapi.api.bukkit.profile;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.game.Game;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.Party;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public abstract class AbstractGameProfile implements GameProfile {
    protected final OfflinePlayer player;
    protected Party<? super GameProfile> party;
    protected Game<? super GameProfile> game;
    protected Map<String, Long> partyInvites;

    protected AbstractGameProfile(@NonNull OfflinePlayer player, Party<? super GameProfile> party) {
        this.player = player;
        this.party = party;
        this.game = null;
        this.partyInvites = new HashMap<>();
    }

    protected AbstractGameProfile(@NonNull OfflinePlayer player) {
        this(player, null);
    }
    
    protected AbstractGameProfile(@NonNull UUID uniqueId, Party<? super GameProfile> party) {
        this(Bukkit.getOfflinePlayer(uniqueId), party);
    }

    protected AbstractGameProfile(@NonNull UUID uniqueId) {
        this(uniqueId, null);
    }
}
