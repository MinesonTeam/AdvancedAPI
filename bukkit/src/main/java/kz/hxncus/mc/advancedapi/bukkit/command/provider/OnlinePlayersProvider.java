package kz.hxncus.mc.advancedapi.bukkit.command.provider;

import kz.hxncus.mc.advancedapi.api.bukkit.command.provider.TabProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class OnlinePlayersProvider implements TabProvider {
    @Override
    public List<String> provide(CommandSender sender) {
        return Bukkit.getOnlinePlayers().stream()
                                        .map(Player::getName)
                                        .collect(Collectors.toList());
    }
}

