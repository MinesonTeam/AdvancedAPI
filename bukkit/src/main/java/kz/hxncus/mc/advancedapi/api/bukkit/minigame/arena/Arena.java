package kz.hxncus.mc.advancedapi.api.bukkit.minigame.arena;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import kz.hxncus.mc.advancedapi.api.bukkit.region.Region;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс для управления ареной мини-игры
 */
public interface Arena extends ConfigurationSerializable {
    /**
     * Получить уникальный идентификатор арены
     */
    @NonNull UUID getUniqueId();
    
    /**
     * Получить создателя арены
     */
    UUID getCreator();

    /**
     * Установить создателя арены
     */
    void setCreator(@NonNull UUID creator);

    /**
     * Получить название арены
     */
    @NonNull String getName();

    /**
     * Установить название арены
     */
    void setName(@NonNull String name);
    
    /**
     * Получить дату создания арены
     */
    long getCreationDate();

    /**
     * Получить мир арены
     */
    @NonNull World getWorld();

    /**
     * Установить мир арены
     */
    void setWorld(@NonNull World world);

    /**
     * Получить статус арены
     */
    @NonNull ArenaState getState();
    
    /**
     * Установить статус арены
     */
    void setState(@NonNull ArenaState state);

    /**
     * Получить точку спавна арены
     */
    @NonNull Location getLobbyLocation();

    /**
     * Установить точку спавна арены
     */
    void setLobbyLocation(@NonNull Location location);
    
    /**
     * Получить границы арены
     */
    @NonNull Region getRegion();

    /**
     * Установить границы арены
     */
    void setRegion(@NonNull Region region);
    
    /**
     * Получить список точек спавна арены
     */
    @NonNull List<Location> getSpawnLocations();

    /**
     * Сбросить арену в исходное состояние
     */
    void reset();

    /**
     * Установить список точек спавна арены
     */
    default boolean addSpawnLocation(@NonNull Location location) {
        return this.getSpawnLocations().add(location);
    }

    default boolean removeSpawnLocation(@NonNull Location location) {
        return this.getSpawnLocations().remove(location);
    }

    default boolean teleportToLobby(@NonNull GameProfile profile) {
        OfflinePlayer player = profile.getPlayer();
        Player onlinePlayer = player.getPlayer();
        if (onlinePlayer == null || onlinePlayer.getWorld().equals(this.getWorld())) {
            return false;
        }
        return onlinePlayer.teleport(this.getLobbyLocation());
    }
}
