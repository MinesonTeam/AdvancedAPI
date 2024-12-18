package kz.hxncus.mc.advancedapi.api.friend;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.google.common.base.Optional;

public interface Friend {
    UUID getUniqueId();
    String getName();

    default OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.getUniqueId());
    }

    default Optional<Player> getOnlinePlayer() {
        return Optional.fromNullable(Bukkit.getPlayer(this.getUniqueId()));
    }

    default boolean isOnline() {
        return this.getOnlinePlayer().isPresent();
    }
}
