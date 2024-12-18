package kz.hxncus.mc.advancedapi.api.bukkit.minigame.team;

import org.bukkit.ChatColor;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.GameProfile;
import kz.hxncus.mc.advancedapi.api.collective.Collective;
import lombok.NonNull;

/**
 * Интерфейс для работы с командами в мини-играх
 * @param <P> тип игрока (обычно Player или UUID)
 */
public interface Team<P extends GameProfile> extends Collective<P> {
    /**
     * Получить цвет команды
     */
    @NonNull ChatColor getColor();

    /**
     * Установить цвет команды
     */
    void setColor(@NonNull ChatColor color);

    /**
     * Получить префикс команды
     */
    String getPrefix();

    /**
     * Установить префикс команды
     */
    void setPrefix(String prefix);
    
    /**
     * Получить суффикс команды
     */
    String getSuffix();
    
    /**
     * Установить суффикс команды
     */
    void setSuffix(String suffix);

    /**
     * Получить статус команды
     */
    TeamState getState();

    /**
     * Установить статус команды
     */
    void setState(TeamState state);
}
