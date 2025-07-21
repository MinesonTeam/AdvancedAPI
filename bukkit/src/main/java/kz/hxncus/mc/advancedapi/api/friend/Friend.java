package kz.hxncus.mc.advancedapi.api.friend;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface Friend extends ConfigurationSerializable {
    UUID getUniqueId();
    String getName();

    default OfflinePlayer toOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.getUniqueId());
    }

    default Optional<Player> toOnlinePlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(this.getUniqueId()));
    }

    default boolean isOnline() {
        return this.toOnlinePlayer().isPresent();
    }
}
