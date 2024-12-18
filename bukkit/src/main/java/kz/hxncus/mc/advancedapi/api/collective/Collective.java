package kz.hxncus.mc.advancedapi.api.collective;

import java.util.List;
import java.util.UUID;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.Profile;
import lombok.NonNull;

public interface Collective<P extends Profile> {
    @NonNull UUID getUniqueId();
    String getName();
    void setName(String name);
    List<P> getProfiles();

    /**
     * Получить максимальное количество игроков
     */
    int getMaxProfiles();
    
    /**
     * Установить максимальное количество игроков
     */
    void setMaxProfiles(int maxProfiles);

    /**
     * Добавить игрока в пати
     */
    default boolean addProfile(@NonNull P profile) {
        if (this.isFull() || this.hasProfile(profile)) {
            return false;
        }
        return this.getProfiles().add(profile);
    }

    default boolean removeProfile(P profile) {
        return this.getProfiles().remove(profile);
    }

    default boolean hasProfile(P profile) {
        return this.getProfiles().contains(profile);
    }

    /**
     * Очистить пати
     */
    default void clear() {
        this.getProfiles().clear();
    }

    default int getProfileCount() {
        return this.getProfiles().size();
    }

    /**
     * Проверить, полное ли пати
     */
    default boolean isFull() {
        return this.getProfileCount() >= this.getMaxProfiles();
    }
}
