package kz.hxncus.mc.minesonapi.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleRandomTest {
    @Test
    void generateRandomKey() {
        RandomValueGenerator<String> generator = new RandomValueGenerator<>(new SimpleRandom());
        generator.add("Test1", 1.65, 110)
                 .add("Test2", 0.95, 10)
                 .add("Test3", 2, 4)
                 .add("Test4", 39, 100);
        Assertions.assertEquals("Test3", generator.getKey(1));
        Assertions.assertEquals("Test3", generator.getKey(2));
        Assertions.assertEquals("Test3", generator.getKey(50));
        Assertions.assertEquals("Test1", generator.getKey(51));
        Assertions.assertEquals("Test2", generator.getKey(52));
        Assertions.assertEquals("Test2", generator.getKey(53));
        Assertions.assertEquals("Test2", generator.getKey(54));
        Assertions.assertEquals("Test2", generator.getKey(55));
        Assertions.assertEquals("Test2", generator.getKey(56));
        Assertions.assertEquals("Test2", generator.getKey(57));
        Assertions.assertEquals("Test2", generator.getKey(58));
        Assertions.assertEquals("Test2", generator.getKey(59));
        Assertions.assertEquals("Test2", generator.getKey(60));
        Assertions.assertEquals("Test2", generator.getKey(61));
        Assertions.assertEquals("Test4", generator.getKey(62));
        Assertions.assertEquals("Test4", generator.getKey(63));
        Assertions.assertEquals("Test4", generator.getKey(100));
    }
}
