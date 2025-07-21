package kz.hxncus.mc.advancedapi.utility.builder;

import org.bukkit.Material;
import org.bukkit.inventory.meta.BannerMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class ItemBuilderTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void meta() {
        new ItemBuilder(Material.BLACK_BANNER).meta(BannerMeta.class, bannerMeta -> {
           bannerMeta.
        });
    }
}
