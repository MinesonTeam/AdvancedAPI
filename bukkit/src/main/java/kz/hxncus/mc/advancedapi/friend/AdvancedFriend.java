package kz.hxncus.mc.advancedapi.friend;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.Profile;
import kz.hxncus.mc.advancedapi.api.friend.AbstractFriend;

/**
 * Represents a friend in the game.
 */
public class AdvancedFriend extends AbstractFriend {
    // Constructor to initialize with UUID and name
    public AdvancedFriend(UUID uuid, String name) {
        super(uuid, name);
    }

    // Factory method to create an AdvancedFriend from a Profile
    public static AdvancedFriend of(Profile profile) {
        return new AdvancedFriend(profile.getUniqueId(), profile.getName());
    }

    // ConfigurationSerializable implementation
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("uniqueId", this.uniqueId);
        map.put("name", this.name);
        return map;
    }

    // Deserialization method
    public static AdvancedFriend deserialize(Map<String, Object> map) {
        UUID uniqueId = UUID.fromString((String) map.get("uniqueId"));
        String name = (String) map.get("name");
        return new AdvancedFriend(uniqueId, name);
    }
}
