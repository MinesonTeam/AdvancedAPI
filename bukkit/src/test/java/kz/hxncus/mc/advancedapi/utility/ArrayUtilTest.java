package kz.hxncus.mc.advancedapi.utility;

import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayUtilTest {
    private final Material[] materials = Material.values();
    private final String[] fruits = {"apple", "banana", "orange", "grape", "pear", "pineapple"};

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNextEnum() {
        // Вызываем метод next
        Material next = ArrayUtil.next(materials, Material.STONE);

        // Проверяем, что возвращаемый элемент является следующим по порядку
		assertEquals(Material.GRANITE, next);
	}
	
    @Test
    void testPreviousEnum() {
		// Вызываем метод previous
        Material next = ArrayUtil.previous(materials, Material.STONE);

        // Проверяем, что возвращаемый элемент является предыдущим по порядку
		assertEquals(Material.AIR, next);
	}
	
    @Test
    void testNext() {
		// Вызываем метод next
        String next = ArrayUtil.next(fruits, "apple");

        // Проверяем, что возвращаемый элемент является следующим по порядку
		assertEquals("banana", next);
	}
	
    @Test
    void testPrevious() {
		// Вызываем метод previous
        String previous = ArrayUtil.previous(fruits, "apple");

        // Проверяем, что возвращаемый элемент является предыдущим по порядку
		assertEquals("pineapple", previous);
	}
	
    @Test
    void indexOf() {
		// Вызываем метод indexOf
        int index = ArrayUtil.indexOf(fruits, "banana");

        // Проверяем, что возвращаемый индекс является правильным
		assertEquals(1, index);
	}
}
