package kz.hxncus.mc.advancedapi.bukkit.profile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.google.common.base.Optional;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.party.Party;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.AbstractGameProfile;
import kz.hxncus.mc.advancedapi.api.friend.Friend;
import kz.hxncus.mc.advancedapi.bukkit.minigame.party.AdvancedParty;
import kz.hxncus.mc.advancedapi.bukkit.minigame.party.PartyService;
import kz.hxncus.mc.advancedapi.friend.AdvancedFriend;
import kz.hxncus.mc.advancedapi.service.ServiceModule;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AdvancedGameProfile extends AbstractGameProfile {
    protected int kills = 0;
    protected int deaths = 0;
    protected int balance = 0;

    public AdvancedGameProfile(@NonNull UUID uniqueId, int kills, int deaths, int balance) {
        super(uniqueId);
        this.kills = kills;
        this.deaths = deaths;
        this.balance = balance;
    }

    public AdvancedGameProfile(@NonNull OfflinePlayer player) {
        super(player);
    }

    public AdvancedGameProfile(@NonNull UUID uniqueId) {
        super(uniqueId);
    }

    public AdvancedGameProfile(@NonNull Player player) {
        super(player);
    }

    public float getKD() {
        return this.kills / this.deaths;
    }

    public Friend asFriend() {
        return AdvancedFriend.of(this);
    }

    @NonNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("player", this.player.getUniqueId().toString());
        data.put("party", this.party.getUniqueId().toString());
        data.put("kills", this.kills);
        data.put("deaths", this.deaths);
        data.put("balance", this.balance);

        return data;
    }

    public static AdvancedGameProfile deserialize(@NonNull Map<String, Object> data) {
        UUID playerUniqueId = UUID.fromString(data.get("player").toString());
        UUID partyUniqueId = data.get("party") != null ? UUID.fromString(data.get("party").toString()) : null;
        int kills = Integer.parseInt(data.get("kills").toString());
        int deaths = Integer.parseInt(data.get("deaths").toString());
        int balance = Integer.parseInt(data.get("balance").toString());

        Optional<PartyService> partyService = ServiceModule.getService(PartyService.class);
        Party<?> party;
        if (partyUniqueId != null && partyService.isPresent()) {
            party = partyService.get().getParty(partyUniqueId);
        } else {
            party = null;
        }
        if (party instanceof AdvancedParty) {
            AdvancedParty typedParty = (AdvancedParty) party;
            AdvancedGameProfile gameProfile = new AdvancedGameProfile(playerUniqueId, kills, deaths, balance);
            gameProfile.setParty(typedParty);
            return gameProfile;
        }
        return new AdvancedGameProfile(playerUniqueId, kills, deaths, balance);
    }
}

