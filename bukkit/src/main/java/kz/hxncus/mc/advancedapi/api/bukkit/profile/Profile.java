package kz.hxncus.mc.advancedapi.api.bukkit.profile;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import kz.hxncus.mc.advancedapi.api.friend.Friend;
import lombok.NonNull;

public interface Profile extends ConfigurationSerializable {
    @NonNull OfflinePlayer getPlayer();
    @NonNull Friend asFriend();

    default @NonNull UUID getUniqueId() {
        return this.getPlayer().getUniqueId();
    }

    default @NonNull String getName() {
        return this.getPlayer().getName();
    }
}
