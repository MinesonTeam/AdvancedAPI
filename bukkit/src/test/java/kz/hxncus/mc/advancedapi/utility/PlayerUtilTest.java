package kz.hxncus.mc.advancedapi.utility;

import kz.hxncus.mc.advancedapi.bukkit.player.PlayerMemento;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class PlayerUtilTest {
    // Mock-объекты которые используються во всех тестах
    @Mock
    private Player player;
    @Mock
    private PlayerMemento playerMemento;

    // Подготовка библиотеки Mockito
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRemember() {
        // Настройка Mock-объектов
        UUID playerId = UUID.randomUUID();
        PlayerInventory inventory = mock(PlayerInventory.class);
        when(player.getUniqueId()).thenReturn(playerId);
        when(player.getInventory()).thenReturn(inventory);

        // Вызываем метод remember
        PlayerUtil.remember(player);

        // Проверка, что мементо был сохранён
        assertTrue(PlayerUtil.getPlayerMementos().containsKey(playerId));
        // Проверка, что мементо не равен null
        assertNotNull(PlayerUtil.getPlayerMementos().get(playerId));
    }

    @Test
    void testRestore() {
        // Настройка Mock-объектов
        UUID playerId = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(playerId);
        when(player.getName()).thenReturn("TestPlayer");

        // Создаем и сохраняем мементо
        PlayerMemento memento = mock(PlayerMemento.class);
        PlayerUtil.getPlayerMementos().put(playerId, memento);
        
        // Проверка, что исключение не было
        assertDoesNotThrow(() -> {
            PlayerUtil.restore(player);
        }, "Player memento not found for player " + player.getName());

        PlayerUtil.getPlayerMementos().put(playerId, memento);
        // Вызываем метод restore
        PlayerUtil.restore(player);
        
        // Проверяем, что метод apply был вызван
        verify(memento, atLeastOnce()).apply();
        // Проверяем, что мементо был удален
        assertFalse(PlayerUtil.getPlayerMementos().containsKey(playerId));
    }

    @Test
    void testRestoreWithNoMemento() {
        // Настройка Mock-объектов
        UUID playerId = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(playerId);
        when(player.getName()).thenReturn("TestPlayer");

        // Пытаемся восстановить игрока без мементо
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PlayerUtil.restore(player);
        });
        // Проверка, что исключение не равен null
        assertNotNull(exception);
    }

    @Test
    void testPlaySoundWithSound() {
        // Вызываем метод playSound
        Sound sound = Sound.BLOCK_ANVIL_LAND;
        PlayerUtil.playSound(player, sound);

        // Проверка, что метод playSound был вызван с теми же параметрами
        verify(player, times(1)).playSound(player.getLocation(), sound, 0f, 100f);
    }

    @Test
    void testPlaySoundWithStringAndCategory() {
        // Вызываем метод playSound
        String soundName = "minecraft:block.note_block.bell";
        SoundCategory category = SoundCategory.AMBIENT;
        PlayerUtil.playSound(player, soundName, category);

        // Проверка, что метод playSound был вызван с теми же параметрами
        verify(player, times(1)).playSound(player.getLocation(), soundName, category, 0f, 100f);
    }

    @Test
    void testReset() {
        // Настройка Mock-объектов
        World world = mock(World.class);
        AttributeInstance attributeInstance = mock(AttributeInstance.class);
        when(player.getWorld()).thenReturn(world);
        when(world.getWorldBorder()).thenReturn(mock(org.bukkit.WorldBorder.class));
        when(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).thenReturn(attributeInstance);
        when(attributeInstance.getValue()).thenReturn(20.0);
        
        // Вызываем метод reset
        PlayerUtil.reset(player);

        // Проверка, что метод вызвал все необходимые методы
        verify(player, times(1)).getWorld();
        verify(world, times(1)).getWorldBorder();
        verify(player, times(1)).getAttribute(Attribute.GENERIC_MAX_HEALTH);
        verify(attributeInstance, times(1)).getValue();
        verify(player, times(1)).setFireTicks(0);
        verify(player, times(1)).setFreezeTicks(0);
        verify(player, times(1)).setFallDistance(0);
        verify(player, times(1)).setGameMode(GameMode.SURVIVAL);
        verify(player, times(1)).getActivePotionEffects();
        verify(player, times(1)).setHealth(anyDouble());
        verify(player, times(1)).setHealthScale(20);
        verify(player, times(1)).setFoodLevel(20);
        verify(player, times(1)).setSaturation(5f);
        verify(player, times(1)).setExp(0);
        verify(player, times(1)).setLevel(0);
        verify(player, times(1)).setWorldBorder(world.getWorldBorder());
        verify(player, times(1)).setVisualFire(false);
        verify(player, times(1)).setWalkSpeed(0.2f);
        verify(player, times(1)).setFlying(false);
    }
}
