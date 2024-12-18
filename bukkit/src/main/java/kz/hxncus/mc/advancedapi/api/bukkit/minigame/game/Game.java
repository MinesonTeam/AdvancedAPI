package kz.hxncus.mc.advancedapi.api.bukkit.minigame.game;

import lombok.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.base.Optional;

import kz.hxncus.mc.advancedapi.api.bukkit.minigame.arena.Arena;
import kz.hxncus.mc.advancedapi.api.bukkit.minigame.team.Team;
import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;

/**
 * Интерфейс для управления мини-игрой
 */
public interface Game<P extends GameProfile> {
    /**
     * Получить уникальный идентификатор игры
     */
    @NonNull UUID getUniqueId();

    /**
     * Получить название игры
     */
    @NonNull String getName();

    /**
     * Установить название игры
     */
    void setName(@NonNull String name);

    /**
     * Получить описание игры
     */
    @NonNull String getDescription();

    /**
     * Установить описание игры
     */
    void setDescription(@NonNull String description);

    /**
     * Получить текущую арену
     */
    @NonNull Arena getArena();

    /**
     * Установить арену
     */
    void setArena(@NonNull Arena arena);

    /**
     * Получить список команд
     */
    @NonNull List<Team<P>> getTeams();
    
    /**
     * Получить статус игры
     */
    @NonNull GameState getState();
    
    /**
     * Установить статус игры
     */
    void setState(@NonNull GameState state);
    
    /**
     * Получить минимальное количество игроков
     */
    int getMinPlayers();
    
    /**
     * Получить максимальное количество игроков
     */
    int getMaxPlayers();
    
    /**
     * Начать игру
     */
    void start();
    
    /**
     * Остановить игру
     */
    void stop();
    
    /**
     * Получить количество игроков
     */
    default int getPlayerCount() {
        return this.getTeams().stream().mapToInt(Team::getProfileCount).sum();
    }
    
    /**
     * Проверить, заполнена ли игра
     */
    default boolean isFull() {
        return this.getPlayerCount() >= this.getMaxPlayers();
    }

    /**
     * Добавить профиль в игру
     */
    default boolean addProfile(@NonNull P profile) {
        if (this.isFull()) {
            return false;
        }
        Optional<Team<P>> team = this.getFreeTeam();
        if (team.isPresent()) {
            team.get().addProfile(profile);
            return true;
        }
        return false;
    }
    
    /**
     * Удалить игрока из игры
     */
    default boolean removeProfile(@NonNull P profile) {
        Optional<Team<P>> team = this.getTeam(profile);
        if (team.isPresent()) {
            team.get().removeProfile(profile);
            return true;
        }
        return false;
    }

    /**
     * Проверить, находится ли игрок в игре
     */
    default boolean hasProfile(@NonNull P profile) {
        return this.getProfiles().contains(profile);
    }

    /**
     * Получить свободную команду
     */
    default Optional<Team<P>> getFreeTeam() {
        Team<P> team = this.getTeams().stream().filter(t -> !t.isFull()).findFirst().orElse(null);
        if (team == null) {
            return Optional.absent();
        }
        return Optional.of(team);
    }
    /**
     * Получить команду игрока
     */
    default Optional<Team<P>> getTeam(@NonNull P profile) {
        for (Team<P> team : this.getTeams()) {
            if (team.getProfiles().contains(profile)) {
                return Optional.of(team);
            }
        }
        return Optional.absent();
    }
    
    /**
     * Получить список игроков
     */
    default @NonNull List<P> getProfiles() {
        return this.getTeams().stream().flatMap(team -> team.getProfiles().stream()).toList();
    }
    
    /**
     * Телепорт всех игроков в арену
     */
    default void teleportPlayersToArena() {
        final Iterator<Location> iterator = this.getArena().getSpawnLocations().iterator();
        for (Team<P> team : this.getTeams()) {
            if (!iterator.hasNext()) {
                this.stop();
                throw new IllegalStateException("Not enough spawn locations");
            }
            Location next = iterator.next();
            for (P profile : team.getProfiles()) {
                Player player = profile.getPlayer().getPlayer();
                if (player == null) {
                    continue;
                }
                player.teleport(next);
            }
        }
    }
    
    /**
     * Телепорт игрока в игру
     */
    default boolean teleportToLobby(@NonNull P profile) {
        return this.getArena().teleportToLobby(profile);
    }

    /**
     * Очистить игру (удалить всех игроков)
     */
    default void clear() {
        this.getTeams().clear();
    }
}
