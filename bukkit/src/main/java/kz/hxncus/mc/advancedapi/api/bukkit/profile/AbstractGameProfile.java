package kz.hxncus.mc.advancedapi.api.bukkit.profile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.fastutil.Hash;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.game.Game;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.Party;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractGameProfile implements GameProfile {
    protected final OfflinePlayer player;
    protected Party<? super GameProfile> party;
    protected Game<? super GameProfile> game;
    protected Map<UUID, Long> partyInvites;

    protected AbstractGameProfile(@NonNull OfflinePlayer player) {
        this.player = player;
        this.party = null;
        this.game = null;
        this.partyInvites = new HashMap<>();
    }

    protected AbstractGameProfile(@NonNull Player player) {
        this(player.getUniqueId());
    }
    
    protected AbstractGameProfile(@NonNull UUID uniqueId) {
        this(Bukkit.getOfflinePlayer(uniqueId));
    }

    protected AbstractGameProfile(@NonNull OfflinePlayer player, @NonNull Party<GameProfile> party, @NonNull Game<GameProfile> game) {
        this.player = player;
        this.party = party;
        this.game = game;
        this.partyInvites = new HashMap<>();
    }
}
