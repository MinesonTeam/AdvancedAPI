package kz.hxncus.mc.advancedapi.utility;

import kz.hxncus.mc.advancedapi.bukkit.player.PlayerMemento;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public final class PlayerUtil {
    @Getter
    private final Map<UUID, PlayerMemento> playerMementos = new HashMap<>();

    public void remember(@NonNull final Player player) {
        PlayerUtil.playerMementos.put(player.getUniqueId(), new PlayerMemento(player));
    }

    public void restore(@NonNull final Player player) {
        final PlayerMemento memento = PlayerUtil.playerMementos.get(player.getUniqueId());
        if (memento == null) {
            throw new IllegalArgumentException("Player memento not found for player " + player.getName());
        }
        memento.apply();
        PlayerUtil.playerMementos.remove(player.getUniqueId());
    }

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

    public void reset(@NonNull final Player player) {
        World world = player.getWorld();
        double max_health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setFireTicks(0);
        player.setFreezeTicks(0);
        player.setFallDistance(0);
        try {
            player.setGameMode(Bukkit.getDefaultGameMode());
        } catch (NullPointerException e) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        player.setHealth(max_health);
        player.setHealthScale(20);
        player.setFoodLevel(20);
        player.setSaturation(5f);
        player.setExp(0);
        player.setLevel(0);
        player.setWorldBorder(world.getWorldBorder());
        player.setVisualFire(false);
        player.setWalkSpeed(0.2f);
        player.setFlying(false);
    }
}
