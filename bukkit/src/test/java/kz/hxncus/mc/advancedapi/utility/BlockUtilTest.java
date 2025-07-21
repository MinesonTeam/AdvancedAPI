package kz.hxncus.mc.advancedapi.utility;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BlockUtilTest {
    // Mock-объекты которые используються во всех тестах
    @Mock
    private Block block;

    // Подготовка библиотеки Mockito
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsPlayerPlacedBlock() {
        // Настройка Mock-объектов
        Location location = mock(Location.class);
        World world = mock(World.class);
        Chunk chunk = mock(Chunk.class);
        PersistentDataContainer dataContainer = mock(PersistentDataContainer.class);

        when(block.getWorld()).thenReturn(world);
        when(block.getChunk()).thenReturn(chunk);
        when(chunk.getPersistentDataContainer()).thenReturn(dataContainer);
        when(block.getLocation()).thenReturn(location);

        NamespacedKey key = NamespacedKey.minecraft(Integer.toString(location.hashCode(), 16)); // Example radix key
        when(dataContainer.has(key, PersistentDataType.INTEGER)).thenReturn(true);

        // Проверка, что метод вернет true
        assertTrue(BlockUtil.isPlayerPlaced(block));
    }

    @Test
    void testGetVeinBlocks() {
        // Настройка Mock-объектов
        for (BlockFace blockFace : BlockUtil.BLOCK_FACES) {
            when(block.getRelative(blockFace)).thenReturn(block);
        }

        // Вызываем метод getVeinBlocks
        Set<Block> result = BlockUtil.getVeinBlocks(block, block -> true);

        // Проверка, что метод вернёт длину 1, потому что вокруг блока нету других блоков
        assertEquals(1, result.size());
        // Проверка, что результат содержит блок
        assertTrue(result.contains(block));
    }

    @Test
    void testSetBlockFaces() {
        // Настройка Mock-объектов
        MultipleFacing mockFacing = mock(MultipleFacing.class);
        BlockUtil.setBlockFaces(mockFacing, true, BlockFace.NORTH, BlockFace.EAST);

        // Проверка, что метод вызвал setFace дважды
        verify(mockFacing).setFace(BlockFace.NORTH, true);
        verify(mockFacing).setFace(BlockFace.EAST, true);
    }

    @Test
    void testGetSign() {
        // Настройка Mock-объектов
        Sign mockSign = mock(Sign.class);

        when(block.getState()).thenReturn(mockSign);

        // Вызываем метод getSign
        Optional<Sign> sign = BlockUtil.getSign(block);

        // Проверка, что табличка не равна null
        assertTrue(sign.isPresent());
        // Проверка, что табличка равна mockSign
        assertEquals(mockSign, sign.get());
    }

    @Test
    void testGetSignLine() {
        // Настройка Mock-объектов
        Sign mockSign = mock(Sign.class);
        SignSide mockSignSide = mock(SignSide.class);

        when(block.getState()).thenReturn(mockSign);
        when(mockSign.getSide(eq(Side.FRONT))).thenReturn(mockSignSide);
        when(mockSignSide.getLine(1)).thenReturn("Hello, World!");

        // Вызываем метод getSignLine
        String line = BlockUtil.getSignLine(block, Side.FRONT, 1);
        // Проверка, что строка равна "Hello, World!"
        assertEquals("Hello, World!", line);
    }

    @Test
    void testSetSignLine() {
        // Настройка Mock-объектов
        Sign mockSign = mock(Sign.class);
        SignSide mockSignSide = mock(SignSide.class);

        when(block.getState()).thenReturn(mockSign);
        when(mockSign.getSide(Side.FRONT)).thenReturn(mockSignSide);

        // Вызываем метод setSignLine
        assertTrue(BlockUtil.setSignLine(block, Side.FRONT, 1, "Hello, Test!"));
        // Проверка, что метод вызвал setLine
        verify(mockSignSide).setLine(1, "Hello, Test!");
    }
}
