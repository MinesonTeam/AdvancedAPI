package kz.hxncus.mc.minesonapi.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MSRandomTest {
    @Test
    void generateRandomKey() {
        MSRandom random = MSRandom.getInstance().add("Test1", 1.65, 110) // 1.5%
                                                                   .add("Test2", 0.95, 10) // 9.5%
                                                                   .add("Test3", 2, 4) // 50%
                                                                   .add("Test4", 39, 100); // 39%
        Assertions.assertEquals(random.getKeyFromEntryMap(1), "Test3");
        Assertions.assertEquals(random.getKeyFromEntryMap(2), "Test3");
        Assertions.assertEquals(random.getKeyFromEntryMap(50), "Test3");
        Assertions.assertEquals(random.getKeyFromEntryMap(51), "Test1");
        Assertions.assertEquals(random.getKeyFromEntryMap(52), "Test2");
        Assertions.assertEquals(random.getKeyFromEntryMap(53), "Test2");
        Assertions.assertEquals(random.getKeyFromEntryMap(54), "Test2");
        Assertions.assertEquals(random.getKeyFromEntryMap(55), "Test2");
        Assertions.assertEquals(random.getKeyFromEntryMap(56), "Test2");
        Assertions.assertEquals(random.getKeyFromEntryMap(57), "Test2");
        Assertions.assertEquals(random.getKeyFromEntryMap(58), "Test2");
        Assertions.assertEquals(random.getKeyFromEntryMap(59), "Test2");
        Assertions.assertEquals(random.getKeyFromEntryMap(60), "Test2");
        Assertions.assertEquals(random.getKeyFromEntryMap(61), "Test2");
        Assertions.assertEquals(random.getKeyFromEntryMap(62), "Test4");
        Assertions.assertEquals(random.getKeyFromEntryMap(63), "Test4");
        Assertions.assertEquals(random.getKeyFromEntryMap(100), "Test4");
    }
}
