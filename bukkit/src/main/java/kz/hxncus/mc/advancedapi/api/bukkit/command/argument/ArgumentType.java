package kz.hxncus.mc.advancedapi.api.bukkit.command.argument;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Registry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public enum ArgumentType {
	ADVANCEMENT(player -> Collections2.transform(Lists.newArrayList(Registry.ADVANCEMENT), advancement -> advancement.getKey().toString())),
	AXIS(player -> Lists.newArrayList("x", "xy", "xyz", "xz", "y", "yz", "z")),
	BIOME(player -> Collections2.transform(Lists.newArrayList(Registry.BIOME), biome -> biome.getKey().toString())),
	BOOLEAN(player -> Lists.newArrayList("true", "false")),
	BOSS_BAR(player -> Collections2.transform(Lists.newArrayList(Registry.BOSS_BARS), bossBar -> bossBar.getKey().toString())),
	CHAT_COLOR(player -> Collections2.transform(Arrays.asList(ChatColor.values()), Enum::name)),
	COORDINATE(player -> Lists.newArrayList(player.getLocation().getX() + "", player.getLocation().getY() + "", player.getLocation().getZ() + "")),
	ENCHANTMENT(player -> Collections2.transform(Lists.newArrayList(Registry.ENCHANTMENT), enchantment -> enchantment.getKey().toString())),
	ENTITY_NAME(player -> Collections2.transform(player.getWorld().getEntities(), Entity::getName)),
	ENTITY_TYPE(player -> Collections2.transform(Lists.newArrayList(Registry.ENTITY_TYPE), entityType -> entityType.getKey().toString())),
	ONLINE_PLAYER_NAME(player -> Collections2.transform(Bukkit.getOnlinePlayers(), Player::getName)),
	OFFLINE_PLAYER_NAME(player -> Collections2.transform(Arrays.asList(Bukkit.getOfflinePlayers()), OfflinePlayer::getName));
	
	final Function<Player, Collection<String>> function;
	
	ArgumentType(Function<Player, Collection<String>> function) {
		this.function = function;
	}
	
	public Collection<String> getList(Player player) {
		return function.apply(player);
	}

	public Collection<String> getList(Player player, Predicate<? super String> filter) {
		Collection<String> filtered = getList(player);
        filtered.removeIf(str -> !filter.test(str));
		return filtered;
	}
}
