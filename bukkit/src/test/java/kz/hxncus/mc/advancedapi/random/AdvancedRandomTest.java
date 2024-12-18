package kz.hxncus.mc.advancedapi.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kz.hxncus.mc.advancedapi.utility.random.AdvancedValueGenerator;

class AdvancedRandomTest {
    @Test
    void generateRandomKey() {
        AdvancedValueGenerator<String> generator = new AdvancedValueGenerator<>();
        generator.add("Test1", 1.65, 110)
                 .add("Test2", 0.95, 10)
                 .add("Test3", 2, 4)
                 .add("Test4", 39, 100);

        Assertions.assertEquals("Test3", generator.getValue(1));
        Assertions.assertEquals("Test3", generator.getValue(2));
        Assertions.assertEquals("Test3", generator.getValue(50));
        Assertions.assertEquals("Test1", generator.getValue(51));
        Assertions.assertEquals("Test2", generator.getValue(52));
        Assertions.assertEquals("Test2", generator.getValue(53));
        Assertions.assertEquals("Test2", generator.getValue(54));
        Assertions.assertEquals("Test2", generator.getValue(55));
        Assertions.assertEquals("Test2", generator.getValue(56));
        Assertions.assertEquals("Test2", generator.getValue(57));
        Assertions.assertEquals("Test2", generator.getValue(58));
        Assertions.assertEquals("Test2", generator.getValue(59));
        Assertions.assertEquals("Test2", generator.getValue(60));
        Assertions.assertEquals("Test2", generator.getValue(61));
        Assertions.assertEquals("Test4", generator.getValue(62));
        Assertions.assertEquals("Test4", generator.getValue(63));
        Assertions.assertEquals("Test4", generator.getValue(100));
    }
}
