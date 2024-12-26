package kz.hxncus.mc.advancedapi.utility;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class PlayerUtil {
    public void playSound(@NonNull final Player player, @NonNull final String sound) {
        player.playSound(player.getLocation(), sound, 0f, 100f);
    }

    public void playSound(@NonNull final Player player, @NonNull final Sound sound) {
        player.playSound(player.getLocation(), sound, 0f, 100f);
    }
    
    public void playSound(@NonNull final Player player, @NonNull final String sound, @NonNull final SoundCategory soundCategory) {
        player.playSound(player.getLocation(), sound, soundCategory, 0f, 100f);
    }

    public void playSound(@NonNull final Player player, @NonNull final Sound sound, @NonNull final SoundCategory soundCategory) {
        player.playSound(player.getLocation(), sound, soundCategory, 0f, 100f);
    }

    public void restore(@NonNull final Player player) {
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(20);
        player.setSaturation(5f);
        player.setFireTicks(0);
        player.setFreezeTicks(0);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
    }

    public List<Player> getNearbyPlayers(@NonNull final Player player, final double x, final double y, final double z) {
        Location location = player.getLocation();
        BoundingBox boundingBox = BoundingBox.of(location, x, y, z);

        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        return onlinePlayers.stream().filter(onlinePlayer -> {
            Location onlinePlayerLocation = onlinePlayer.getLocation();
            return boundingBox.contains(onlinePlayerLocation.getX(), onlinePlayerLocation.getY(), onlinePlayerLocation.getZ());
        }).collect(Collectors.toList());
    }

    public List<Player> getNearbyPlayers(@NonNull final Player player, final double radius) {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        return onlinePlayers.stream().filter(p -> p.getLocation().distance(player.getLocation()) <= radius).collect(Collectors.toList());
    }
}
