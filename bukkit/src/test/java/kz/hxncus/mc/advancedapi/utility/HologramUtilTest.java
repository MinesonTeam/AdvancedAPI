package kz.hxncus.mc.advancedapi.utility;

import kz.hxncus.mc.advancedapi.api.bukkit.hologram.Hologram;
import org.bukkit.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HologramUtilTest {
    // Mock-объекты которые используються во всех тестах
    @Mock
    private Hologram hologram;

    // Подготовка библиотеки Mockito
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateHologram() {
        // Настройка Mock-объектов
        String hologramName = "TestHologram";

        when(hologram.getName()).thenReturn(hologramName);
        doNothing().when(hologram).spawn();
        doNothing().when(hologram).despawn();

        // Вызываем метод createHologram
        Hologram createdHologram = HologramUtil.createHologram(() -> hologram);

        // Проверка, что созданная голограмма совпадает с ожидаемой
        assertEquals(hologram, createdHologram);
        // Проверка, что голограмма присутствует
        assertTrue(HologramUtil.getHOLOGRAMS().containsKey(hologramName));
    }

    @Test
    void testDeleteHologram() {
        // Настройка Mock-объектов
        String hologramName = "TestHologram";

        when(hologram.getName()).thenReturn(hologramName);
        doNothing().when(hologram).despawn();

        // Вызываем метод добавление голограммы
        HologramUtil.getHOLOGRAMS().put(hologramName, hologram);
        // Вызываем метод deleteHologram
        HologramUtil.deleteHologram(hologram);

        // Проверка, что голограмма не присутствует
        assertFalse(HologramUtil.getHOLOGRAMS().containsKey(hologramName));
        // Проверка, что метод despawn был вызван
        verify(hologram, times(1)).despawn();
    }

    @Test
    void testSpawnAllHolograms() {
        // Настройка Mock-объектов
        doNothing().when(hologram).spawn();

        // Вызываем метод добавление голограммы
        HologramUtil.getHOLOGRAMS().put("TestHologram", hologram);
        // Вызываем метод spawnAllHolograms
        HologramUtil.spawnAllHolograms();

        // Проверка, что метод spawn был вызван
        verify(hologram, times(1)).spawn();
    }

    @Test
    void testUpdateAllHolograms() {
        // Настройка Mock-объекта
        doNothing().when(hologram).update();

        // Добавление холограммы в карту
        HologramUtil.getHOLOGRAMS().put("TestHologram", hologram);

        // Вызов метода updateAllHolograms
        HologramUtil.updateAllHolograms();

        // Проверка, что метод update был вызван
        verify(hologram, times(1)).update();
    }

    @Test
    void testAddLine() {
        // Настройка Mock-объектов
        String hologramName = "TestHologram";
        String line = "Test Line";

        doNothing().when(hologram).addLine(line);

        // Добавление холограммы в карту
        HologramUtil.getHOLOGRAMS().put(hologramName, hologram);

        // Вызов метода addLine
        HologramUtil.addLine(hologramName, line);

        // Проверка, что метод addLine был вызван
        verify(hologram, times(1)).addLine(line);
    }

    @Test
    void testRemoveLine() {
        // Настройка Mock-объектов
        String hologramName = "TestHologram";
        String line = "Test line 1";
        int index = 0;

        List<String> lines = new ArrayList<>();
        lines.add(line);
        lines.add("Test line 2");

        // Проверка что в списке есть элементы
        assertTrue(lines.size() >= 1);
        when(hologram.getLines()).thenReturn(lines);

        // Вызов метода добавления голограммы
        HologramUtil.getHOLOGRAMS().put(hologramName, hologram);
        // Вызов метода removeLine
        HologramUtil.removeLine(hologramName, index);

        // Проверка, что линия была удалена
        assertFalse(lines.contains(line));
    }

    @Test
    void testMoveHologram() {
        // Настройка Mock-объектов
        String hologramName = "TestHologram";
        Location location = mock(Location.class);

        when(hologram.isSpawned()).thenReturn(true);

        // Добавление голограммы
        HologramUtil.getHOLOGRAMS().put(hologramName, hologram);
        // Вызов метода moveHologram
        HologramUtil.moveHologram(hologramName, location);

        // Проверка, что метод move был вызван
        verify(hologram, times(1)).move(location);
    }

    @Test
    void testSpawnHologram() {
        // Настройка Mock-объектов
        String hologramName = "TestHologram";

        doNothing().when(hologram).spawn();

        // Вызов метода добавленеи голограммы
        HologramUtil.getHOLOGRAMS().put(hologramName, hologram);
        // Вызов метода spawnHologram
        HologramUtil.spawnHologram(hologramName);

        // Проверка, что метод spawn был вызван
        verify(hologram, times(1)).spawn();
    }

    @Test
    void testDespawnHologram() {
        // Настройка Mock-объектов
        String hologramName = "TestHologram";

        doNothing().when(hologram).despawn();

        // Вызов метода добавления голограммы
        HologramUtil.getHOLOGRAMS().put(hologramName, hologram);
        // Вызов метода despawnHologram
        HologramUtil.despawnHologram(hologramName);

        // Проверка, что метод despawn был вызван
        verify(hologram, times(1)).despawn();
    }

    @Test
    void testUpdateHologram() {
        // Настройка Mock-объектов
        String hologramName = "TestHologram";

        doNothing().when(hologram).update();

        // Вызов метода добавления голограммы
        HologramUtil.getHOLOGRAMS().put(hologramName, hologram);
        // Вызов метода updateHologram
        HologramUtil.updateHologram(hologramName);

        // Проверка, что метод update был вызван
        verify(hologram, times(1)).update();
    }
}
