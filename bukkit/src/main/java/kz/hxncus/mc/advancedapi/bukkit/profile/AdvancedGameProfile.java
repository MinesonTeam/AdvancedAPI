package kz.hxncus.mc.advancedapi.bukkit.profile;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.Party;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.AbstractGameProfile;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import kz.hxncus.mc.advancedapi.api.friend.Friend;
import kz.hxncus.mc.advancedapi.bukkit.friend.AdvancedFriend;
import kz.hxncus.mc.advancedapi.bukkit.minigame.party.AdvancedParty;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdvancedGameProfile extends AbstractGameProfile {
    protected Map<String, Double> properties;

    public AdvancedGameProfile(@NonNull UUID uniqueId) {
        super(uniqueId);
        this.properties = new HashMap<>();
    }

    public AdvancedGameProfile(@NonNull OfflinePlayer player) {
        super(player);
    }

    public AdvancedGameProfile(@NonNull UUID uniqueId, @NonNull Party<GameProfile> party) {
        super(uniqueId, party);
    }

    public Friend asFriend() {
        return AdvancedFriend.of(this);
    }

    @NonNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("player", this.player.getUniqueId().toString());
        data.put("party", this.party.getUniqueId().toString());
        data.put("properties", this.properties);

        return data;
    }

    public static AdvancedGameProfile deserialize(@NonNull Map<String, Object> data) {
        UUID playerUniqueId = UUID.fromString((String) data.get("player"));
        UUID partyUniqueId = data.get("party") != null ? UUID.fromString((String) data.get("party")) : null;
        int kills = Integer.parseInt((String) data.get("kills"));
        int deaths = Integer.parseInt((String) data.get("deaths"));
        int balance = Integer.parseInt((String) data.get("balance"));

        Party<?> party = null;
//        if (partyUniqueId != null) {
//            PartyController partyController = plugin.getPartyController();
//            party = partyController.getParty(partyUniqueId);
//        }
        if (party instanceof AdvancedParty) {
            AdvancedParty typedParty = (AdvancedParty) party;
            AdvancedGameProfile gameProfile = new AdvancedGameProfile(playerUniqueId, typedParty);
            gameProfile.setParty(typedParty);
            return gameProfile;
        }
        return new AdvancedGameProfile(playerUniqueId);
    }
}
