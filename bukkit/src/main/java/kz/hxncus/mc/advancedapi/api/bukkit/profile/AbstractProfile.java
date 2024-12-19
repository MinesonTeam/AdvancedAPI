package kz.hxncus.mc.advancedapi.api.bukkit.profile;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import kz.hxncus.mc.advancedapi.api.friend.Friend;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class AbstractProfile implements Profile {
    protected final OfflinePlayer player;
    protected final Set<Friend> friends;
    
    protected AbstractProfile(@NonNull OfflinePlayer player) {
        this.player = player;
        this.friends = new LinkedHashSet<>();
    }

    protected AbstractProfile(@NonNull UUID uniqueId) {
        this(Bukkit.getOfflinePlayer(uniqueId));
    }

    protected AbstractProfile(@NonNull Player player) {
        this(player.getUniqueId());
    }

    protected AbstractProfile(@NonNull OfflinePlayer player, @NonNull Set<Friend> friends) {
        this.player = player;
        this.friends = friends;
    }
}
