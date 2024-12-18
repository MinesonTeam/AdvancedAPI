package kz.hxncus.mc.advancedapi.bukkit.command.argument;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.AbstractArgument;

public class PlayerArgument extends AbstractArgument<Player> {
    public PlayerArgument(final String name) {
        super(name);
    }

    @Override
    public Player parse(String arg) {
        Player player = Bukkit.getPlayer((String) arg);
        return player;
    }

    @Override
    public Class<Player> getType() {
        return Player.class;
    }
    
}
