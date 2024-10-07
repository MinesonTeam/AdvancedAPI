package kz.hxncus.mc.minesonapi.api.bukkit.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum ArgumentType {
	ONLINE_PLAYERS((o) -> Bukkit.getOnlinePlayers().stream().map(Player::getName).toList()),
	OFFLINE_PLAYERS((o) -> Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList());
	final Function<Object, List<String>> function;
	ArgumentType(Function<Object, List<String>> function) {
		this.function = function;
	}
	List<String> getList(Object obj) {
		return function.apply(obj);
	}
}
