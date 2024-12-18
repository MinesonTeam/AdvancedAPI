package kz.hxncus.mc.advancedapi.api.bukkit.command.argument;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.BiomeProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

public enum ArgumentType {
	ADVANCEMENT(player -> Collections2.transform(Lists.newArrayList(Registry.ADVANCEMENT), advancement -> advancement.getKey().toString())),
	AXIS(player -> Set.of("x", "xy", "xyz", "xz", "y", "yz", "z")),
	BIOME(player -> Collections2.transform(Lists.newArrayList(Registry.BIOME), biome -> biome.getKey().toString())),
	BOOLEAN(player -> Set.of("true", "false")),
	BOSS_BAR(player -> Collections2.transform(Lists.newArrayList(Registry.BOSS_BARS), bossBar -> bossBar.getKey().toString())),
	CHAT_COLOR(player -> Collections2.transform(Arrays.asList(ChatColor.values()), Enum::name)),
	COORDINATE(player -> Set.of(player.getLocation().getX() + "", player.getLocation().getY() + "", player.getLocation().getZ() + "")),
	CURRENT_WORLD_BIOME(player -> {
		World world = player.getWorld();
		BiomeProvider biomeProvider = world.getBiomeProvider();
		if (biomeProvider == null) {
			return Collections.emptyList();
		}
		return Collections2.transform(biomeProvider.getBiomes(world),biome -> biome.getKey().toString());
	}),
	ENCHANTMENT(player -> Collections2.transform(Lists.newArrayList(Registry.ENCHANTMENT), enchantment -> enchantment.getKey().toString())),
	ENTITY_NAME(player -> Collections2.transform(player.getWorld().getEntities(), Entity::getName)),
	ENTITY_TYPE((player) -> Collections2.transform(Lists.newArrayList(Registry.ENTITY_TYPE), entityType -> entityType.getKey().toString())),
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
		return Collections2.filter(getList(player), filter);
	}
}
