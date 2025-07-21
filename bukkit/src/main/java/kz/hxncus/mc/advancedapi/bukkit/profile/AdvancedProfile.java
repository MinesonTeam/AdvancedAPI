package kz.hxncus.mc.advancedapi.bukkit.profile;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.AbstractProfile;
import kz.hxncus.mc.advancedapi.api.friend.Friend;
import kz.hxncus.mc.advancedapi.bukkit.friend.AdvancedFriend;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

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

        Map<String, Object> friends = new HashMap<String, Object>();
        this.friends.forEach(friend -> {
            friends.put(friend.getName(), friend.getUniqueId().toString());
        });

        data.put("friends", friends);

        return data;
    }

    public static AdvancedProfile deserialize(@NonNull Map<String, Object> data) {
        UUID playerUniqueId = UUID.fromString((String) data.get("player"));
        Set<Friend> friends = new LinkedHashSet<>();
        ((Map<String, Object>) data.get("friends")).forEach((name, uuid) -> {
            friends.add(new AdvancedFriend(UUID.fromString((String) uuid), name));
        });

        return new AdvancedProfile(playerUniqueId);
    }

    @Override
    public @NonNull Friend asFriend() {
        return AdvancedFriend.of(this);
    }
}
