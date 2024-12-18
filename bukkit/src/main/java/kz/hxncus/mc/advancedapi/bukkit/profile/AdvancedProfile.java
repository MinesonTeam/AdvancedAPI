package kz.hxncus.mc.advancedapi.bukkit.profile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.AbstractProfile;
import kz.hxncus.mc.advancedapi.api.friend.Friend;
import kz.hxncus.mc.advancedapi.friend.AdvancedFriend;
import lombok.NonNull;

public class AdvancedProfile extends AbstractProfile {
    public AdvancedProfile(@NonNull OfflinePlayer player) {
        super(player);
    }

    public AdvancedProfile(@NonNull UUID uniqueId) {
        super(uniqueId);
    }

    public AdvancedProfile(@NonNull Player player) {
        super(player);
    }

    @NonNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("player", this.player.getUniqueId().toString());

        return data;
    }

    public static AdvancedProfile deserialize(@NonNull Map<String, Object> data) {
        UUID playerUniqueId = UUID.fromString(data.get("player").toString());
        
        return new AdvancedProfile(playerUniqueId);
    }

    @Override
    public @NonNull Friend asFriend() {
        return AdvancedFriend.of(this);
    }
}
