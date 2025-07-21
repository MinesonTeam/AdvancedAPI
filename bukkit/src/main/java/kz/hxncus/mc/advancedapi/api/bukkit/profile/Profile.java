package kz.hxncus.mc.advancedapi.api.bukkit.profile;

import kz.hxncus.mc.advancedapi.api.friend.Friend;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.UUID;

public interface Profile extends ConfigurationSerializable {
    @NonNull OfflinePlayer getPlayer();
    @NonNull Friend asFriend();

    default @NonNull UUID getUniqueId() {
        return this.getPlayer().getUniqueId();
    }

    default @NonNull String getName() {
        return this.getPlayer().getName();
    }

    default @NonNull boolean isOnline() {
        return this.getPlayer().isOnline();
    }

    default boolean hasPlayedBefore() {
        return this.getPlayer().hasPlayedBefore();
    }

    default long getFirstPlayed() {
        return this.getPlayer().getFirstPlayed();
    }

    default long getLastPlayed() {
        return this.getPlayer().getLastPlayed();
    }
}
