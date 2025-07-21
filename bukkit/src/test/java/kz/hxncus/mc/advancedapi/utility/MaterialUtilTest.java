package kz.hxncus.mc.advancedapi.utility;

import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaterialUtilTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsWool() {
        assertTrue(MaterialUtil.isWool(Material.WHITE_WOOL));
        assertTrue(MaterialUtil.isWool(Material.ORANGE_WOOL));
        assertTrue(MaterialUtil.isWool(Material.MAGENTA_WOOL));
        assertTrue(MaterialUtil.isWool(Material.LIGHT_BLUE_WOOL));
        assertTrue(MaterialUtil.isWool(Material.YELLOW_WOOL));
        assertTrue(MaterialUtil.isWool(Material.LIME_WOOL));
        assertTrue(MaterialUtil.isWool(Material.PINK_WOOL));
        assertTrue(MaterialUtil.isWool(Material.GRAY_WOOL));
        assertTrue(MaterialUtil.isWool(Material.LIGHT_GRAY_WOOL));
        assertTrue(MaterialUtil.isWool(Material.CYAN_WOOL));
        assertTrue(MaterialUtil.isWool(Material.PURPLE_WOOL));
        assertTrue(MaterialUtil.isWool(Material.BLUE_WOOL));
        assertTrue(MaterialUtil.isWool(Material.BROWN_WOOL));
        assertTrue(MaterialUtil.isWool(Material.GREEN_WOOL));
        assertTrue(MaterialUtil.isWool(Material.RED_WOOL));
        assertTrue(MaterialUtil.isWool(Material.BLACK_WOOL));
        assertFalse(MaterialUtil.isWool(null));
        assertFalse(MaterialUtil.isWool(Material.AIR));
        assertFalse(MaterialUtil.isWool(Material.STONE));
        assertFalse(MaterialUtil.isWool(Material.BARRIER));
    }

    @Test
    void testIsLog() {
        assertTrue(MaterialUtil.isLog(Material.ACACIA_LOG));
        assertTrue(MaterialUtil.isLog(Material.BIRCH_LOG));
        assertTrue(MaterialUtil.isLog(Material.DARK_OAK_LOG));
        assertTrue(MaterialUtil.isLog(Material.JUNGLE_LOG));
        assertTrue(MaterialUtil.isLog(Material.OAK_LOG));
        assertTrue(MaterialUtil.isLog(Material.SPRUCE_LOG));
        assertFalse(MaterialUtil.isLog(null));
        assertFalse(MaterialUtil.isLog(Material.AIR));
        assertFalse(MaterialUtil.isLog(Material.STONE));
        assertFalse(MaterialUtil.isLog(Material.BARRIER));
    }

    @Test
    void testIsLeave() {
        assertTrue(MaterialUtil.isLeave(Material.ACACIA_LEAVES));
        assertTrue(MaterialUtil.isLeave(Material.BIRCH_LEAVES));
        assertTrue(MaterialUtil.isLeave(Material.DARK_OAK_LEAVES));
        assertTrue(MaterialUtil.isLeave(Material.JUNGLE_LEAVES));
        assertTrue(MaterialUtil.isLeave(Material.OAK_LEAVES));
        assertTrue(MaterialUtil.isLeave(Material.SPRUCE_LEAVES));
        assertFalse(MaterialUtil.isLeave(null));
        assertFalse(MaterialUtil.isLeave(Material.AIR));
        assertFalse(MaterialUtil.isLeave(Material.STONE));
        assertFalse(MaterialUtil.isLeave(Material.BARRIER));
    }

    @Test
    void testIsSword() {
        assertTrue(MaterialUtil.isSword(Material.WOODEN_SWORD));
        assertTrue(MaterialUtil.isSword(Material.STONE_SWORD));
        assertTrue(MaterialUtil.isSword(Material.IRON_SWORD));
        assertTrue(MaterialUtil.isSword(Material.GOLDEN_SWORD));
        assertTrue(MaterialUtil.isSword(Material.DIAMOND_SWORD));
        assertFalse(MaterialUtil.isSword(null));
        assertFalse(MaterialUtil.isSword(Material.AIR));
        assertFalse(MaterialUtil.isSword(Material.STONE));
        assertFalse(MaterialUtil.isSword(Material.BARRIER));
    }

    @Test
    void testIsPickaxe() {
        assertTrue(MaterialUtil.isPickaxe(Material.WOODEN_PICKAXE));
        assertTrue(MaterialUtil.isPickaxe(Material.STONE_PICKAXE));
        assertTrue(MaterialUtil.isPickaxe(Material.IRON_PICKAXE));
        assertTrue(MaterialUtil.isPickaxe(Material.GOLDEN_PICKAXE));
        assertTrue(MaterialUtil.isPickaxe(Material.DIAMOND_PICKAXE));
        assertFalse(MaterialUtil.isPickaxe(null));
        assertFalse(MaterialUtil.isPickaxe(Material.AIR));
        assertFalse(MaterialUtil.isPickaxe(Material.STONE));
        assertFalse(MaterialUtil.isPickaxe(Material.BARRIER));
    }

    @Test
    void testIsShovel() {
        assertTrue(MaterialUtil.isShovel(Material.WOODEN_SHOVEL));
        assertTrue(MaterialUtil.isShovel(Material.STONE_SHOVEL));
        assertTrue(MaterialUtil.isShovel(Material.IRON_SHOVEL));
        assertTrue(MaterialUtil.isShovel(Material.GOLDEN_SHOVEL));
        assertTrue(MaterialUtil.isShovel(Material.DIAMOND_SHOVEL));
        assertFalse(MaterialUtil.isShovel(null));
        assertFalse(MaterialUtil.isShovel(Material.AIR));
        assertFalse(MaterialUtil.isShovel(Material.STONE));
        assertFalse(MaterialUtil.isShovel(Material.BARRIER));
    }

    @Test
    void testIsAxe() {
        assertTrue(MaterialUtil.isAxe(Material.WOODEN_AXE));
        assertTrue(MaterialUtil.isAxe(Material.STONE_AXE));
        assertTrue(MaterialUtil.isAxe(Material.IRON_AXE));
        assertTrue(MaterialUtil.isAxe(Material.GOLDEN_AXE));
        assertTrue(MaterialUtil.isAxe(Material.DIAMOND_AXE));
        assertFalse(MaterialUtil.isAxe(null));
        assertFalse(MaterialUtil.isAxe(Material.AIR));
        assertFalse(MaterialUtil.isAxe(Material.STONE));
        assertFalse(MaterialUtil.isAxe(Material.BARRIER));
    }

    @Test
    void testIsHoe() {
        assertTrue(MaterialUtil.isHoe(Material.WOODEN_HOE));
        assertTrue(MaterialUtil.isHoe(Material.STONE_HOE));
        assertTrue(MaterialUtil.isHoe(Material.IRON_HOE));
        assertTrue(MaterialUtil.isHoe(Material.GOLDEN_HOE));
        assertTrue(MaterialUtil.isHoe(Material.DIAMOND_HOE));
        assertFalse(MaterialUtil.isHoe(null));
        assertFalse(MaterialUtil.isHoe(Material.AIR));
        assertFalse(MaterialUtil.isHoe(Material.STONE));
        assertFalse(MaterialUtil.isHoe(Material.BARRIER));
    }

    @Test
    void testIsPotion() {
        assertTrue(MaterialUtil.isPotion(Material.POTION));
        assertTrue(MaterialUtil.isPotion(Material.SPLASH_POTION));
        assertTrue(MaterialUtil.isPotion(Material.LINGERING_POTION));
        assertFalse(MaterialUtil.isPotion(null));
        assertFalse(MaterialUtil.isPotion(Material.AIR));
        assertFalse(MaterialUtil.isPotion(Material.STONE));
        assertFalse(MaterialUtil.isPotion(Material.BARRIER));
    }

    @Test
    void testIsSpawnEgg() {
        assertTrue(MaterialUtil.isSpawnEgg(Material.ZOMBIE_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.SKELETON_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.CREEPER_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.SPIDER_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.ENDERMAN_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.SILVERFISH_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.BLAZE_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.GHAST_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.MAGMA_CUBE_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.BAT_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.WITCH_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.SLIME_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.GHAST_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.PIG_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.SHEEP_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.COW_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.CHICKEN_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.SQUID_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.WOLF_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.OCELOT_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.RABBIT_SPAWN_EGG));
        assertTrue(MaterialUtil.isSpawnEgg(Material.VILLAGER_SPAWN_EGG));
        assertFalse(MaterialUtil.isSpawnEgg(null));
        assertFalse(MaterialUtil.isSpawnEgg(Material.AIR));
    }

    @Test
    void testIsHead() {
        assertTrue(MaterialUtil.isHead(Material.PLAYER_HEAD));
        assertTrue(MaterialUtil.isHead(Material.ZOMBIE_HEAD));
        assertTrue(MaterialUtil.isHead(Material.CREEPER_HEAD));
        assertTrue(MaterialUtil.isHead(Material.DRAGON_HEAD));
        assertTrue(MaterialUtil.isHead(Material.SKELETON_SKULL));
        assertTrue(MaterialUtil.isHead(Material.WITHER_SKELETON_SKULL));
        assertTrue(MaterialUtil.isHead(Material.PLAYER_WALL_HEAD));
        assertTrue(MaterialUtil.isHead(Material.ZOMBIE_WALL_HEAD));
        assertTrue(MaterialUtil.isHead(Material.CREEPER_WALL_HEAD));
        assertTrue(MaterialUtil.isHead(Material.DRAGON_WALL_HEAD));
        assertTrue(MaterialUtil.isHead(Material.SKELETON_WALL_SKULL));
        assertTrue(MaterialUtil.isHead(Material.WITHER_SKELETON_WALL_SKULL));
        assertFalse(MaterialUtil.isHead(null));
        assertFalse(MaterialUtil.isHead(Material.AIR));
        assertFalse(MaterialUtil.isHead(Material.STONE));
        assertFalse(MaterialUtil.isHead(Material.BARRIER));
    }

    @Test
    void testIsMinecart() {
        assertTrue(MaterialUtil.isMinecart(Material.MINECART));
        assertTrue(MaterialUtil.isMinecart(Material.CHEST_MINECART));
        assertTrue(MaterialUtil.isMinecart(Material.FURNACE_MINECART));
        assertTrue(MaterialUtil.isMinecart(Material.TNT_MINECART));
        assertTrue(MaterialUtil.isMinecart(Material.HOPPER_MINECART));
        assertTrue(MaterialUtil.isMinecart(Material.COMMAND_BLOCK_MINECART));
        assertFalse(MaterialUtil.isMinecart(null));
        assertFalse(MaterialUtil.isMinecart(Material.AIR));
        assertFalse(MaterialUtil.isMinecart(Material.STONE));
        assertFalse(MaterialUtil.isMinecart(Material.BARRIER));
    }

    @Test
    void testIsBoat() {
        assertTrue(MaterialUtil.isBoat(Material.ACACIA_BOAT));
        assertTrue(MaterialUtil.isBoat(Material.BIRCH_BOAT));
        assertTrue(MaterialUtil.isBoat(Material.DARK_OAK_BOAT));
        assertTrue(MaterialUtil.isBoat(Material.JUNGLE_BOAT));
        assertTrue(MaterialUtil.isBoat(Material.OAK_BOAT));
        assertTrue(MaterialUtil.isBoat(Material.SPRUCE_BOAT));
        assertFalse(MaterialUtil.isBoat(null));
        assertFalse(MaterialUtil.isBoat(Material.AIR));
        assertFalse(MaterialUtil.isBoat(Material.STONE));
        assertFalse(MaterialUtil.isBoat(Material.BARRIER));
    }

    @Test
    void testIsBanner() {
        assertTrue(MaterialUtil.isBanner(Material.WHITE_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.ORANGE_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.MAGENTA_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.LIGHT_BLUE_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.YELLOW_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.LIME_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.PINK_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.GRAY_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.LIGHT_GRAY_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.CYAN_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.PURPLE_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.BLUE_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.BROWN_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.GREEN_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.RED_BANNER));
        assertTrue(MaterialUtil.isBanner(Material.BLACK_BANNER));
        assertFalse(MaterialUtil.isBanner(null));
        assertFalse(MaterialUtil.isBanner(Material.AIR));
        assertFalse(MaterialUtil.isBanner(Material.STONE));
        assertFalse(MaterialUtil.isBanner(Material.BARRIER));
    }
}
