package kz.hxncus.mc.advancedapi.bukkit.command.argument;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.AbstractArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerArgument extends AbstractArgument {
    public PlayerArgument(final String nodeName) {
        super(nodeName);
    }

    @Override
    public Player parse(String arg) {
        Player player;
        try {
            UUID uniqueId = UUID.fromString(arg);
            player = Bukkit.getPlayer(uniqueId);
        } catch (IllegalArgumentException e) {
            player = Bukkit.getPlayer(arg);
        }
        return player;
    }

    @Override
    public Class<Player> getType() {
        return Player.class;
    }
    
}
