package kz.hxncus.mc.advancedapi.utility;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Утилитный класс для работы с сообщениями
 * @since 1.0
 */
@UtilityClass
public final class MessageUtil {
    /**
     * Отправляет сообщение игроку с поддержкой цветовых кодов
     * @param sender получатель сообщения
     * @param message текст сообщения
     * @throws IllegalArgumentException если sender или message равны null
     */
    public void sendMessage(@NonNull CommandSender sender, @NonNull String message) {
        sender.sendMessage(ColorUtil.process(message));
    }

    /**
     * Отправляет сообщение всем игрокам на сервере
     * @param message текст сообщения для рассылки
     */
    public void broadcast(@NonNull String message) {
        Bukkit.getOnlinePlayers().forEach(player -> 
            MessageUtil.sendMessage(player, message)
        );
    }

    /**
     * Отправляет сообщение игроку
     * @param player игрок
     * @param message сообщение
     */
    public void sendMessage(@NonNull Player player, @NonNull String message) {
        player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText(ColorUtil.process(message)));
    }

    /**
     * Отправляет ActionBar сообщение игроку
     * @param player игрок
     * @param message сообщение
     */
    public void sendActionBar(@NonNull Player player, @NonNull String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtil.process(message)));
    }

    /**
     * Отправляет ActionBar сообщение всем игрокам
     * @param message сообщение
     */
    public void broadcastActionBar(@NonNull String message) {
        Bukkit.getOnlinePlayers().forEach(player -> sendActionBar(player, message));
    }
}
